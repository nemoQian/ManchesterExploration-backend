package com.fyp.qian.mapservice.service;

import com.fyp.qian.model.pojo.SelectTree;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import com.fyp.qian.model.pojo.response.PlaceResponse;
import com.fyp.qian.model.pojo.PlacePoint;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Yihan Qian
* @description 针对表【place_point】的数据库操作Service
* @createDate 2024-05-29 17:12:37
*/
public interface PlacePointService extends IService<PlacePoint> {

    /**
     *
     * @param locationSearchRequest
     * @return
     */
    List<PlaceResponse> findPlacePoint(LocationSearchRequest locationSearchRequest);

    /**
     *
     * @return
     */
    List<SelectTree> findPlacePointCategories();
}
