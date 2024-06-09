package com.fyp.qian.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.*;
import com.fyp.qian.model.pojo.request.PlaceTagOptionRequest;
import com.fyp.qian.model.pojo.request.TagWaitingListRequest;
import com.fyp.qian.userservice.service.PlaceTagsService;
import com.fyp.qian.userservice.mapper.PlaceTagsMapper;
import com.google.gson.Gson;
import io.micrometer.common.util.StringUtils;
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

    @Override
    public Long InsertPlaceTag(PlaceTags placeTags, HttpServletRequest request) {
        if(placeTags==null){
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        if (StringUtils.isBlank(placeTags.getTagName())) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        if (placeTags.getTagParentId() == null) {
            placeTags.setTagParentId(-1L);
        }

        if (placeTags.getTagShownStatus() == null) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        QueryWrapper<PlaceTags> placeTagsQueryWrapper = new QueryWrapper<>();
        placeTagsQueryWrapper.eq("osm_id", placeTags.getOsmId());
        placeTagsQueryWrapper.eq("tag_parent_id", placeTags.getTagParentId());
        placeTagsQueryWrapper.eq("tag_shown_status", placeTags.getTagShownStatus());
        placeTagsQueryWrapper.eq("tag_name", placeTags.getTagName());
        placeTagsQueryWrapper.eq("tag_belongs", placeTags.getTagBelongs());
        long count = this.count(placeTagsQueryWrapper);
        if (count > 0) {
            throw new BusinessException(StateEnum.DUPLICATE_TAG_ERROR);
        }

        if (placeTags.getTagShownStatus() == 0) {
            placeTags.setApprovalState(1);
        }
        this.save(placeTags);
        return null;
    }

    @Override
    public String placeTagsListShown(HttpServletRequest request) {
        return generateGlobalPlaceTags();
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

    private String generateGlobalPlaceTags(){
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
        Gson gson = new Gson();

        return gson.toJson(tagsTreeList);
    }

    private TagsTree fillInTagData(PlaceTags tag, boolean isLeaf) {
        TagsTree tagsTree = new TagsTree();
        tagsTree.setKey(tag.getTagName());
        tagsTree.setTitle(tag.getTagName());
        tagsTree.setLeaf(isLeaf);
        tagsTree.setChildren(new ArrayList<>());

        return tagsTree;
    }
}




