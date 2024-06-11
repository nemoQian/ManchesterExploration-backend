package com.fyp.qian.userservice.service;

import com.fyp.qian.model.pojo.PlaceTags;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.qian.model.pojo.SelectTree;
import com.fyp.qian.model.pojo.TagsTree;
import com.fyp.qian.model.pojo.request.PlaceTagInsertRequest;
import com.fyp.qian.model.pojo.request.PlaceTagOptionRequest;
import com.fyp.qian.model.pojo.request.TagWaitingListRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author Yihan Qian
* @description 针对表【place_tags(tag table)】的数据库操作Service
* @createDate 2024-06-05 14:08:32
*/
public interface PlaceTagsService extends IService<PlaceTags> {

    /**
     *
     * @param placeTagsRequest
     * @param request
     * @return
     */
    Long InsertPlaceTag (PlaceTagInsertRequest placeTagsRequest, HttpServletRequest request);

    /**
     *
     * @param request
     * @return
     */
    String placeTagsListShown (HttpServletRequest request);

    /**
     *
     * @param tagName
     * @return
     */
    List<Long> searchPlaceByTag (String tagName);

    /**
     *
     * @param placeTagOptionRequest
     * @param request
     * @return
     */
    List<SelectTree> queryPlaceTagOption (PlaceTagOptionRequest placeTagOptionRequest, HttpServletRequest request);
}
