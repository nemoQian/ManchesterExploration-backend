package com.fyp.qian.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.AvatarUpload;
import com.fyp.qian.model.pojo.User;
import com.fyp.qian.model.pojo.response.TagUserListResponse;
import com.fyp.qian.userservice.mapper.UserMapper;
import com.fyp.qian.common.utils.EncryptUtil;
import com.fyp.qian.userservice.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.fyp.qian.common.constant.UserConstant.*;

/**
* @author Yihan Qian
* @createDate 2024-04-18 19:26:43
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AvatarUpload token;

    @Override
    public long userRegister(String username, String userPassword, String checkPassword) {
        inputValidation(username, USERNAME_LENGTH, USERNAME_TYPE);

        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(StateEnum.PASSWORD_COMPARED_ERROR);
        }

        inputValidation(userPassword, PASSWORD_LENGTH, PASSWORD_TYPE);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(StateEnum.DUPLICATE_USER_ERROR);
        }

        String encryptedPassword = EncryptUtil.encrypt(userPassword);

        User user = new User();
        user.setUsername(username);
        user.setUserPassword(encryptedPassword);

        if (!this.save(user)) {
            throw new BusinessException(StateEnum.REQUEST_ERROR, "register in database error");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String username, String userPassword, HttpServletRequest request) {
        inputValidation(username, USERNAME_LENGTH, USERNAME_TYPE);

        inputValidation(userPassword, PASSWORD_LENGTH, PASSWORD_TYPE);

        String encryptedPassword = EncryptUtil.encrypt(userPassword);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("user_password", encryptedPassword);
        User user = this.getOne(queryWrapper);

        if (user == null) {
            throw new BusinessException(StateEnum.NO_RESULTS_ERROR, "User is not found");
        }

        User safetyUser = removeSensitiveInfo(user);
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public List<User> usersList(String username, HttpServletRequest request) {
        if (isAdmin(request)) {
            return null;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = this.list(queryWrapper);

        return userList.stream().map(user -> this.removeSensitiveInfo(user)).collect(Collectors.toList());
    }

    @Override
    public boolean userDelete(long id, HttpServletRequest request) {
        if (isAdmin(request) || id < 0) {
            throw new BusinessException(StateEnum.REQUEST_ERROR);
        }

        return this.removeById(id);
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            return null;
        }
        long userId = currentUser.getId();
        return removeSensitiveInfo(this.getById(userId));
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public List<TagUserListResponse> searchUsersByTags(List<String> tags, boolean strict) {
        if (tags == null || tags.isEmpty()) {
            return null;
        }
        List<User> userList = strict ? searchUsersByTagsInMemory(tags) : searchUsersByTagsInDB(tags);
        List<TagUserListResponse> tagUserListResponses = new ArrayList<>();

        for (User u : userList) {
            TagUserListResponse tagUserListResponse = new TagUserListResponse();
            tagUserListResponse.setNickname(u.getNickname());

            tagUserListResponse.setGender(u.getGender() == null ? -1 : u.getGender());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            tagUserListResponse.setUpdateDate(formatter.format(u.getUpdateTime()));

            Gson gson = new Gson();
            List<String> tagList = gson.fromJson(u.getTags(), new TypeToken<List<String>>() {
            }.getType());
            tagUserListResponse.setTags(tagList);
            tagUserListResponse.setUserId(u.getId());
            tagUserListResponses.add(tagUserListResponse);
        }

        return tagUserListResponses;
    }

    @Override
    public int updateUserInfo(String email, String phone, String nickname, String gender, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_USER_ERROR);
        }
        if (!StringUtils.isBlank(email)) {
            inputValidation(email, EMAIL_LENGTH, EMAIL_TYPE);
        }
        if (!StringUtils.isBlank(phone)) {
            inputValidation(phone, PHONE_LENGTH, PHONE_TYPE);
        }
        currentUser.setEmail(email);
        currentUser.setPhone(phone);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nickname", nickname);

        User user = this.getOne(queryWrapper);

        if(user != null && !Objects.equals(user.getId(), currentUser.getId())){
            throw new BusinessException(StateEnum.DUPLICATE_NICKNAME_ERROR);
        }
        currentUser.setNickname(nickname);

        if(!gender.equals("Male") && !gender.equals("Female")){
            throw new BusinessException(StateEnum.GENDER_INVALID_ERROR) ;
        }
        currentUser.setGender(gender.equals("Male") ? 1 : 0);

        this.updateById(currentUser);

        return StateEnum.UPDATE_SUCCESS.getCode();
    }

    @Override
    public int updateUserAvatar(MultipartFile avatarFile, HttpServletRequest request) {
        if (avatarFile.isEmpty()) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_USER_ERROR);
        }

        String avatarUrl = "";
        Configuration cfg = new Configuration(Region.region1());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(token.getAccessKey(), token.getSecretKey());
        String upToken = auth.uploadToken(token.getBucketName());

        try {
            Response response = uploadManager.put(avatarFile.getInputStream(), avatarFile.getOriginalFilename(), upToken, null, null);

            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            avatarUrl = "http://sdmtifcxe.hb-bkt.clouddn.com/" + putRet.key;

            currentUser.setAvatarUrl(avatarUrl);
            this.updateById(currentUser);

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.out.println(r);
            throw new BusinessException(StateEnum.AVATAR_UPDATE_FAILED, r.error);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return StateEnum.UPDATE_SUCCESS.getCode();
    }

    @Override
    public int updateUserTags(List<String> tags, HttpServletRequest request) {
        if(tags == null || tags.isEmpty()){
            return StateEnum.UPDATE_SUCCESS.getCode();
        }

        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_USER_ERROR);
        }

        Gson gson = new Gson();
        Set<String> newTags = new HashSet<>(tags);

        currentUser.setTags(gson.toJson(newTags));

        this.updateById(currentUser);

        return StateEnum.UPDATE_SUCCESS.getCode();
    }

    private void inputValidation(String input, int inputLength, String inputType) {
        if (StringUtils.isBlank(input) && (USERNAME_TYPE.equals(inputType) || PASSWORD_TYPE.equals(inputType))) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        if (input.length() < inputLength) {
            if(USERNAME_TYPE.equals(inputType)){ throw new BusinessException(StateEnum.USERNAME_LENGTH_ERROR); }

            if (PASSWORD_TYPE.equals(inputType)) { throw new BusinessException(StateEnum.PASSWORD_LENGTH_ERROR); }
        }

        if(!characterCheck(input, inputType)){
            switch (inputType) {
                case "username":
                    throw new BusinessException(StateEnum.USERNAME_INVALID_ERROR);
                case "password":
                    throw new BusinessException(StateEnum.PASSWORD_INVALID_ERROR);
                case "email":
                    throw new BusinessException(StateEnum.EMAIL_INVALID_ERROR);
                case "phone":
                    throw new BusinessException(StateEnum.PHONE_INVALID_ERROR);
            }
        }
    }

    private boolean characterCheck(String input, String inputType) {
        final String SPEC_CHARACTERS = " !\"#$%&'()*+,-./:;<=>?@\\]\\[^_`{|}~";
        final String NO_SPECIAL_CHARACTERS = "^[a-zA-Z0-9]*$";
        final String PUR_NUMERIC = "[0-9]{1,}$";
        final String PUR_LETTER = "[a-zA-Z]{1,}$";
        final String NUM_AND_LETTER = "((^[a-zA-Z]{1,}[0-9]{1,}[a-zA-Z0-9]*)+)" +
                "|((^[0-9]{1,}[a-zA-Z]{1,}[a-zA-Z0-9]*)+)$";
        final String EMAIL_VAL = "[a-zA-Z0-9]+[\\.]{0,1}[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]+";

        boolean result;
        boolean hasSpecChar = false;

        switch (inputType) {
            case "username":
                result = Pattern.compile(NO_SPECIAL_CHARACTERS).matcher(input).find();
                break;
            case "password":
                String targetInput = input;
                char[] inputArray = targetInput.toCharArray();
                for (char c : inputArray) {
                    if (SPEC_CHARACTERS.contains(String.valueOf(c))) {
                        hasSpecChar = true;
                        targetInput = targetInput.replace(c, ' ');
                    }
                }
                targetInput = targetInput.replace(" ", "");
                boolean isPureNum = Pattern.compile(PUR_NUMERIC).matcher(targetInput).find();
                boolean isPureLet = Pattern.compile(PUR_LETTER).matcher(targetInput).find();
                boolean isNumAndLet = Pattern.compile(NUM_AND_LETTER).matcher(targetInput).find();

                result = (isPureNum && hasSpecChar) || (isPureLet && hasSpecChar) || isNumAndLet;
                break;
            case "email":
                result = Pattern.compile(EMAIL_VAL).matcher(input).find();
                break;
            case "phone":
                result = Pattern.compile(PUR_NUMERIC).matcher(input).find();
                break;
            default:
                result = false;
        }

        return result;
    }

    private User removeSensitiveInfo(User user) {
        if (user == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setNickname(user.getNickname());
        safetyUser.setGender(user.getGender());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUpdateTime(user.getUpdateTime());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setDeleteState(user.getDeleteState());
        safetyUser.setTags(user.getTags());
        return safetyUser;
    }

    private boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        return user == null || user.getUserRole() != ADMIN_ROLE;
    }

    private List<User> searchUsersByTagsInDB(List<String> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        for (String tagName : tags) {
            queryWrapper = queryWrapper.like("tags", tagName);
        }

        List<User> users = this.list(queryWrapper);

        return users.stream().map(this::removeSensitiveInfo).collect(Collectors.toList());
    }

    private List<User> searchUsersByTagsInMemory(List<String> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = this.list(queryWrapper);
        Gson gson = new Gson();

        return userList.stream().filter(user -> {
            String tagStr = user.getTags();
            if (StringUtils.isBlank(tagStr)) {
                return false;
            }
            Set<String> tagList = gson.fromJson(tagStr, new TypeToken<Set<String>>() {
            }.getType());
            for (String tag : tags) {
                if(!tagList.contains(tag)) {
                    return false;
                }
            }
            return true;
        }).map(this::removeSensitiveInfo).collect(Collectors.toList());
    }
}




