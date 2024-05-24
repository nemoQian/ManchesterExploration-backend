package com.fyp.qian.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.Tags;
import com.fyp.qian.model.pojo.TagsTree;
import com.fyp.qian.model.pojo.User;
import com.fyp.qian.userservice.mapper.TagsMapper;
import com.fyp.qian.userservice.service.TagsService;
import com.google.gson.Gson;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.fyp.qian.common.constant.UserConstant.*;

/**
* @author Yihan Qian
* @description 针对表【tags(tag table)】的数据库操作Service实现
* @createDate 2024-04-28 16:34:35
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    private String defaultNodeTree = "-E-E";
    @Override
    public int insertTags(String parentTagName, String tagName, int available, HttpServletRequest request) {
        if (StringUtils.isBlank(tagName)) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }
        QueryWrapper<Tags> queryWrapper = new QueryWrapper<>();
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);

        tagName = tagName.toLowerCase();
        if (StringUtils.isBlank(parentTagName)){
            Tags newTags = addNewTags(tagName, currentUser.getId(), available);
            long tagId = newTags.getId();
            newTags.setTagParentId(-1L);
            newTags.setTagAvailable(available);
            String nodeTree = generateNodeTree(ROOT_TYPE, defaultNodeTree, available == 0 ? tagId + "D" : tagId + "");
            newTags.setTagNode(nodeTree);
            this.updateById(newTags);
            return StateEnum.SUCCESS.getCode();
        }

        parentTagName = parentTagName.toLowerCase();
        queryWrapper.eq("tag_name", parentTagName);
        Tags parentTag = this.getOne(queryWrapper);
        if(parentTag == null){
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        Tags newTags = addNewTags(tagName, currentUser.getId(), available);
        long parentTagId = parentTag.getId();
        long tagId = newTags.getId();

        newTags.setTagParentId(parentTag.getId());
        newTags.setTagAvailable(available);

        String parentNodeTree = generateNodeTree(CHILD_TYPE, parentTag.getTagNode(), available == 0 ? tagId + "D" : tagId + "");
        String childNodeTree = generateNodeTree(PARENT_TYPE, parentTag.getTagNode(), parentTag.getTagAvailable() == 0 ? parentTagId + "D" : parentTagId + "");

        parentTag.setTagNode(parentNodeTree);
        newTags.setTagNode(childNodeTree);

        this.updateById(parentTag);
        this.updateById(newTags);

        return StateEnum.SUCCESS.getCode();
    }

    @Override
    public String getRootTagsList() {
        QueryWrapper<Tags> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_parent_id", -1);
        List<Tags> tags = this.list(queryWrapper);

        if(tags == null || tags.size() == 0){
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        List<TagsTree> tagsTreeList = new ArrayList<>();
        for(Tags tag : tags){
            tagsTreeList.add(fillInTagData(tag, false));
        }

        Gson gson = new Gson();

        return gson.toJson(tagsTreeList);
    }

    @Override
    public String getChildTags(String parentTagName) {
        QueryWrapper<Tags> queryWrapper = new QueryWrapper<>();

        parentTagName = parentTagName.replace('+', ' ');
        queryWrapper.eq("tag_name", parentTagName);
        Tags tags = this.getOne(queryWrapper);
        if(tags == null){
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR, "There is no child tags.");
        }

        String tagNodeTree = tags.getTagNode();
        String[] tagNodesTreeArray = tagNodeTree.split("-");
        if (tagNodesTreeArray[2].charAt(0) == 'E') {
            return "";
        }

        String[] childNodeArray = tagNodesTreeArray[2].split("&");
        List<TagsTree> tagsTreeList = new ArrayList<>();
        for(String childNode : childNodeArray){
            if (childNode.contains("D")) {
                childNode = childNode.substring(0, childNode.length()-1);
            }
            int childId = Integer.parseInt(childNode);
            queryWrapper.clear();
            queryWrapper.eq("id", childId);
            Tags childTag = this.getOne(queryWrapper);
            if(childTag == null){
                throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
            }
            String[] currentTagNodeArray = childTag.getTagNode().split("-");
            tagsTreeList.add(fillInTagData(childTag, currentTagNodeArray[2].contains("E")));
        }

        Gson gson = new Gson();

        return gson.toJson(tagsTreeList);
    }

    private Tags addNewTags(String tagName, long usrId, int available) {
        QueryWrapper<Tags> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_name", tagName);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(StateEnum.DUPLICATE_TAG_ERROR);
        }
        Tags tag = new Tags();
        tag.setTagName(tagName);
        tag.setUserId(usrId);
        tag.setTagNode(defaultNodeTree);
        tag.setTagParentId(-1L);
        tag.setTagAvailable(available
        );
        if (!this.save(tag)) {
            throw new BusinessException(StateEnum.REQUEST_ERROR, "Tags Insert Error");
        }

        return tag;
    }

    private String generateNodeTree(String generateType, String nodeTree, String tagId) {
        String newNodeTree = "";
        String[] nodeTreeArray = nodeTree.split("-");

        switch (generateType) {
            case "newRoot":
                newNodeTree = tagId + nodeTree;
                break;
            case "newParent":
                nodeTreeArray[1] = tagId;
                newNodeTree = nodeTreeArray[0] + "-" + nodeTreeArray[1] + "-" + 'E';
                break;
            case "newChild":
                nodeTreeArray[2] = nodeTreeArray[2].charAt(0) == 'E' ? tagId : nodeTreeArray[2] + "&" + tagId;
                newNodeTree = nodeTreeArray[0] + "-" + nodeTreeArray[1] + "-" + nodeTreeArray[2];
                break;
        }

        return newNodeTree;
    }

    private TagsTree fillInTagData(Tags tag, boolean isLeaf) {
        TagsTree tagsTree = new TagsTree();
        tagsTree.setKey(tag.getTagName());
        tagsTree.setTitle(tag.getTagName());
        tagsTree.setDisabled(tag.getTagAvailable() == 0);
        tagsTree.setLeaf(isLeaf);
        tagsTree.setChildren(new ArrayList<>());

        return tagsTree;
    }
}




