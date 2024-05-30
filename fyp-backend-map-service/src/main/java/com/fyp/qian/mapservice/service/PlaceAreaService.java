package com.fyp.qian.mapservice.service;

import com.fyp.qian.model.pojo.PlaceArea;
import com.baomidou.mybatisplus.extension.service.IService;
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
     * @param placeName
     * @return
     */
    List<PlaceResponse> findPlaceAreaByName(String placeName);


}
