package com.fyp.qian.userservice.service;

import com.fyp.qian.model.pojo.PlaceTags;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.qian.model.pojo.TagsTree;
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
     * @param placeTags
     * @param request
     * @return
     */
    Long InsertPlaceTag (PlaceTags placeTags, HttpServletRequest request);

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
}
