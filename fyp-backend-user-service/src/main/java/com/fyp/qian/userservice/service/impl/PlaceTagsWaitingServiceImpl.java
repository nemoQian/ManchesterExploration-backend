package com.fyp.qian.userservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.PlaceTagsWaiting;
import com.fyp.qian.model.pojo.User;
import com.fyp.qian.model.pojo.request.PageRequest;
import com.fyp.qian.model.pojo.request.PlaceTagWaitingListRequest;
import com.fyp.qian.model.pojo.request.TagWaitingListRequest;
import com.fyp.qian.userservice.service.PlaceTagsWaitingService;
import com.fyp.qian.userservice.mapper.PlaceTagsWaitingMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fyp.qian.common.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Yihan Qian
* @description 针对表【place_tags_waiting(tag insert waiting list table)】的数据库操作Service实现
* @createDate 2024-06-09 15:45:34
*/
@Service
public class PlaceTagsWaitingServiceImpl extends ServiceImpl<PlaceTagsWaitingMapper, PlaceTagsWaiting>
    implements PlaceTagsWaitingService{

    @Override
    public Long insertPlaceTagWaitingList(TagWaitingListRequest tagWaitingListRequest, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_LOGIN_ERROR);
        }
        if (tagWaitingListRequest == null) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        PlaceTagsWaiting placeTagsWaiting = new PlaceTagsWaiting();
        placeTagsWaiting.setOsmId(tagWaitingListRequest.getOsmId());
        placeTagsWaiting.setUserId(currentUser.getId());
        placeTagsWaiting.setOsmName(tagWaitingListRequest.getPlaceName());

        this.save(placeTagsWaiting);
        return placeTagsWaiting.getId();
    }

    @Override
    public Long deletePlaceTagWaitingList(Long placeTagWaitingListId) {
        this.removeById(placeTagWaitingListId);
        return placeTagWaitingListId;
    }

    @Override
    public Page<PlaceTagWaitingListRequest> queryPlaceTagWaitingList(PageRequest pageRequest, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_LOGIN_ERROR);
        }

        Page<PlaceTagWaitingListRequest> page = new Page<>();
        Page<PlaceTagsWaiting> placeTagsWaitingPage = new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize());
        Page<PlaceTagsWaiting> placeTagsWaitingDate = this.page(placeTagsWaitingPage);

        page.setCurrent(placeTagsWaitingDate.getCurrent());
        page.setSize(placeTagsWaitingDate.getSize());
        page.setTotal(placeTagsWaitingDate.getTotal());

        List<PlaceTagWaitingListRequest> responses = placeTagsWaitingDate.getRecords().stream().map(placeTagsWaiting -> {
            PlaceTagWaitingListRequest placeTagWaitingListResponse = new PlaceTagWaitingListRequest();
            placeTagWaitingListResponse.setId(placeTagsWaiting.getId());
            placeTagWaitingListResponse.setOsmId(placeTagsWaiting.getOsmId());
            placeTagWaitingListResponse.setPlaceName(placeTagsWaiting.getOsmName());
            placeTagWaitingListResponse.setCreateDate(placeTagsWaiting.getCreateTime());
            placeTagWaitingListResponse.setTagVisibility(2);
            return placeTagWaitingListResponse;
        }).toList();

        page.setRecords(responses);
        return page;
    }
}




