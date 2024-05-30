package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.mapservice.service.POISearchService;
import com.fyp.qian.mapservice.service.PlaceAreaService;
import com.fyp.qian.mapservice.service.PlacePointService;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.response.PlaceResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class POISearchServiceImpl implements POISearchService {

    @Resource
    private PlacePointService placePointService;

    @Resource
    private PlaceAreaService placeAreaService;

    @Override
    public List<PlaceResponse> findPlaceByName(String placeName) {
        List<PlaceResponse> placePoint = placePointService.findPlacePointByName(placeName);
        List<PlaceResponse> placeArea = placeAreaService.findPlaceAreaByName(placeName);
        List<PlaceResponse> result = new ArrayList<>();
        result.addAll(placePoint);
        result.addAll(placeArea);

        if(result.isEmpty()){
            throw new BusinessException(StateEnum.NO_PLACE_FOUND_ERROR);
        }

        return result;
    }
}
