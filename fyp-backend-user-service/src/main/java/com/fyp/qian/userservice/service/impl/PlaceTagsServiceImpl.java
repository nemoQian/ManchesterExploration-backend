package com.fyp.qian.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.*;
import com.fyp.qian.model.pojo.request.PlaceTagInsertRequest;
import com.fyp.qian.model.pojo.request.PlaceTagOptionRequest;
import com.fyp.qian.model.pojo.request.TagWaitingListRequest;
import com.fyp.qian.userservice.service.PlaceTagsService;
import com.fyp.qian.userservice.mapper.PlaceTagsMapper;
import com.fyp.qian.userservice.service.PlaceTagsWaitingService;
import com.fyp.qian.userservice.service.UserGroupRelationService;
import com.google.gson.Gson;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.fyp.qian.common.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Yihan Qian
* @description 针对表【place_tags(tag table)】的数据库操作Service实现
* @createDate 2024-06-05 14:08:32
*/
@Service
public class PlaceTagsServiceImpl extends ServiceImpl<PlaceTagsMapper, PlaceTags>
    implements PlaceTagsService{

    @Resource
    private PlaceTagsWaitingService placeTagsWaitingService;

    @Resource
    private UserGroupRelationService userGroupRelationService;

    @Override
    public Long InsertPlaceTag(PlaceTagInsertRequest placeTagsRequest, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_LOGIN_ERROR);
        }
        PlaceTags placeTags = new PlaceTags();
        if(placeTagsRequest==null){
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        if (StringUtils.isBlank(placeTagsRequest.getTagName())) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        if (placeTagsRequest.getTagParentId() == null || placeTagsRequest.getTagParentId() == 0) {
            placeTags.setTagParentId(-1L);
            placeTagsRequest.setTagParentId(-1L);
            placeTagsRequest.setOsmId(0L);
        }

        if (placeTagsRequest.getTagVisibility() == null) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        if (placeTagsRequest.getTagVisibility() == 2){
            placeTagsRequest.setTagBelongs(currentUser.getId());
        }

        QueryWrapper<PlaceTags> placeTagsQueryWrapper = new QueryWrapper<>();
        placeTagsQueryWrapper.eq("osm_id", placeTagsRequest.getOsmId());
        placeTagsQueryWrapper.eq("tag_parent_id", placeTagsRequest.getTagParentId());
        placeTagsQueryWrapper.eq("tag_shown_status", placeTagsRequest.getTagVisibility());
        placeTagsQueryWrapper.eq("tag_name", placeTagsRequest.getTagName());
        placeTagsQueryWrapper.eq("tag_belongs", placeTagsRequest.getTagBelongs());
        long count = this.count(placeTagsQueryWrapper);
        if (count > 0) {
            throw new BusinessException(StateEnum.DUPLICATE_TAG_ERROR);
        }

        if (placeTagsRequest.getTagVisibility() == 0) {
            placeTags.setApprovalState(1);
        }
        else {
            placeTags.setApprovalState(0);
        }

        placeTags.setUserId(currentUser.getId());
        placeTags.setTagName(placeTagsRequest.getTagName());
        placeTags.setTagBelongs(placeTagsRequest.getTagBelongs());
        placeTags.setTagParentId(placeTagsRequest.getTagParentId());
        placeTags.setOsmId(placeTagsRequest.getOsmId());
        placeTags.setTagShownStatus(placeTagsRequest.getTagVisibility());
        this.save(placeTags);

        placeTagsWaitingService.removeById(placeTagsRequest.getId());

        return placeTags.getId();
    }

    @Override
    public String placeTagsListShown(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        List<TagsTree> tagsTreeList = generateGlobalPlaceTags();
        Gson gson = new Gson();
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            return gson.toJson(tagsTreeList);
        }
        tagsTreeList.addAll(generatePlaceTagsTree(currentUser.getId()));
        return gson.toJson(tagsTreeList);
    }

    @Override
    public List<Long> searchPlaceByTag(String tagName) {
        QueryWrapper<PlaceTags> placeTagsQueryWrapper = new QueryWrapper<>();
        placeTagsQueryWrapper.eq("tag_name", tagName);
        placeTagsQueryWrapper.eq("approval_state", 0);
        List<Long> osmIds = new ArrayList<>();
        for(PlaceTags placeTags : this.list(placeTagsQueryWrapper)){
            osmIds.add(placeTags.getOsmId());
        }
        return osmIds;
    }

    @Override
    public List<SelectTree> queryPlaceTagOption(PlaceTagOptionRequest placeTagOptionRequest, HttpServletRequest request) {
        if(placeTagOptionRequest==null){
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_LOGIN_ERROR);
        }

        QueryWrapper<PlaceTags> placeTagsQueryWrapper = new QueryWrapper<>();
        placeTagsQueryWrapper.eq("tag_parent_id", -1);

        if (placeTagOptionRequest.getTagShownState() == 0) {
            placeTagsQueryWrapper.eq("tag_shown_status", 0);
            placeTagsQueryWrapper.eq("approval_state", 0);
        }

        if(placeTagOptionRequest.getTagShownState() == 1){
            placeTagsQueryWrapper.eq("tag_shown_status", 1);
            placeTagsQueryWrapper.eq("tag_belongs", placeTagOptionRequest.getTagBelongs());
        }

        if(placeTagOptionRequest.getTagShownState() == 2){
            placeTagsQueryWrapper.eq("tag_shown_status", 2);
            placeTagsQueryWrapper.eq("tag_belongs", currentUser.getId());
        }

        List<PlaceTags> placeTagsList = this.list(placeTagsQueryWrapper);
        List<SelectTree> selectTrees = new ArrayList<>();
        for (PlaceTags placeTags : placeTagsList) {
            SelectTree selectTree = new SelectTree();
            selectTree.setLabel(placeTags.getTagName());
            selectTree.setValue(placeTags.getId().toString());
            selectTrees.add(selectTree);
        }
        return selectTrees;
    }

    private List<Long> getGroupIdList(Long userId) {
        List<Long> groupIdList = new ArrayList<>();
        QueryWrapper<UserGroupRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserGroupRelation> userGroupRelations = userGroupRelationService.list(queryWrapper);
        for (UserGroupRelation userGroupRelation : userGroupRelations) {
            groupIdList.add(userGroupRelation.getGroupId());
        }
        return groupIdList;
    }

    private List<TagsTree> generatePlaceTagsTree(Long userId){
        QueryWrapper<PlaceTags> placeTagsQueryWrapper = new QueryWrapper<>();
        List<Long> getGroupIdList = getGroupIdList(userId);
        List<PlaceTags> list = this.baseMapper.selectDistinctTagsByTagBelongs(userId,-1,2,0);
        List<TagsTree> tagsTreeList = new ArrayList<>();
        placeTagsQueryWrapper.clear();
        for(PlaceTags placeTags : list){
            TagsTree tagsTree = fillInTagData(placeTags, false);
            List<PlaceTags> childTags = this.baseMapper.selectDistinctTagsWithoutId(placeTags.getId(),2,0);
            List<TagsTree> childTagsTree = new ArrayList<>();
            for (PlaceTags childTag : childTags){
                TagsTree childTagTree = fillInTagData(childTag,true);
                childTagsTree.add(childTagTree);
            }
            tagsTree.setChildren(childTagsTree);
            tagsTreeList.add(tagsTree);
        }

        list.clear();
        for(Long groupId : getGroupIdList){
            list.addAll(this.baseMapper.selectDistinctTagsByTagBelongs(groupId, -1, 1, 0));
        }
        placeTagsQueryWrapper.clear();
        for(PlaceTags placeTags : list){
            TagsTree tagsTree = fillInTagData(placeTags, false);
            List<PlaceTags> childTags = this.baseMapper.selectDistinctTagsWithoutId(placeTags.getId(),1,0);
            List<TagsTree> childTagsTree = new ArrayList<>();
            for (PlaceTags childTag : childTags){
                TagsTree childTagTree = fillInTagData(childTag,true);
                childTagsTree.add(childTagTree);
            }
            tagsTree.setChildren(childTagsTree);
            tagsTreeList.add(tagsTree);
        }

        return tagsTreeList;
    }

    private List<TagsTree> generateGlobalPlaceTags(){
        QueryWrapper<PlaceTags> placeTagsQueryWrapper = new QueryWrapper<>();
        List<PlaceTags> list = this.baseMapper.selectDistinctTagsWithId(-1,0,0);
        List<TagsTree> tagsTreeList = new ArrayList<>();
        placeTagsQueryWrapper.clear();
        for(PlaceTags placeTags : list){
            TagsTree tagsTree = fillInTagData(placeTags, false);
            List<PlaceTags> childTags = this.baseMapper.selectDistinctTagsWithoutId(placeTags.getId(),0,0);
            List<TagsTree> childTagsTree = new ArrayList<>();
            for (PlaceTags childTag : childTags){
                TagsTree childTagTree = fillInTagData(childTag,true);
                childTagsTree.add(childTagTree);
            }
            tagsTree.setChildren(childTagsTree);
            tagsTreeList.add(tagsTree);
        }

        return tagsTreeList;
    }

    private TagsTree fillInTagData(PlaceTags tag, boolean isLeaf) {
        TagsTree tagsTree = new TagsTree();
        tagsTree.setKey(tag.getTagName());
        tagsTree.setTitle(tag.getTagName());
        tagsTree.setLeaf(isLeaf);
        tagsTree.setChildren(new ArrayList<>());
        if(!isLeaf){
            tagsTree.setDisabled(true);
        }

        return tagsTree;
    }
}




