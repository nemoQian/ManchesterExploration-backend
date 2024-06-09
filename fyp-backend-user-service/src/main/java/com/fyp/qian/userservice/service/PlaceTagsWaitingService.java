package com.fyp.qian.userservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.qian.model.pojo.PlaceTagsWaiting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.qian.model.pojo.request.PageRequest;
import com.fyp.qian.model.pojo.request.PlaceTagWaitingListRequest;
import com.fyp.qian.model.pojo.request.TagWaitingListRequest;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Yihan Qian
* @description 针对表【place_tags_waiting(tag insert waiting list table)】的数据库操作Service
* @createDate 2024-06-09 15:45:34
*/
public interface PlaceTagsWaitingService extends IService<PlaceTagsWaiting> {

    /**
     *
     * @param tagWaitingListRequest
     * @param request
     * @return
     */
    Long insertPlaceTagWaitingList(TagWaitingListRequest tagWaitingListRequest, HttpServletRequest request);

    /**
     *
     * @param request
     * @return
     */
    Page<PlaceTagWaitingListRequest> queryPlaceTagWaitingList (PageRequest pageRequest, HttpServletRequest request);
}
