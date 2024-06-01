package com.fyp.qian.mapservice.service;

import com.fyp.qian.model.pojo.PlaceArea;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.qian.model.pojo.SelectTree;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import com.fyp.qian.model.pojo.response.PlaceResponse;

import java.util.List;

/**
* @author Yihan Qian
* @description 针对表【place_area】的数据库操作Service
* @createDate 2024-05-30 15:02:04
*/
public interface PlaceAreaService extends IService<PlaceArea> {

    /**
     *
     * @param locationSearchRequest
     * @return
     */
    List<PlaceResponse> findPlaceArea(LocationSearchRequest locationSearchRequest);

    /**
     *
     * @return
     */
    List<SelectTree> findPlaceAreaCategories();
}
