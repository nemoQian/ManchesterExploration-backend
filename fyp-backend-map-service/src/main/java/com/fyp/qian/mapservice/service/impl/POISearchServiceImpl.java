package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.mapservice.service.POISearchService;
import com.fyp.qian.mapservice.service.PlaceAreaService;
import com.fyp.qian.mapservice.service.PlacePointService;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.SelectTree;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import com.fyp.qian.model.pojo.response.PlaceResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class POISearchServiceImpl implements POISearchService {

    @Resource
    private PlacePointService placePointService;

    @Resource
    private PlaceAreaService placeAreaService;

    @Override
    public List<PlaceResponse> findPlace(LocationSearchRequest locationSearchRequest) {
        List<PlaceResponse> placePoint = placePointService.findPlacePoint(locationSearchRequest);
        List<PlaceResponse> placeArea = placeAreaService.findPlaceArea(locationSearchRequest);
        List<PlaceResponse> result = new ArrayList<>();
        result.addAll(placePoint);
        result.addAll(placeArea);

        if(result.isEmpty()){
            throw new BusinessException(StateEnum.NO_PLACE_FOUND_ERROR);
        }

        return result;
    }

    @Override
    public List<SelectTree> findPOICategories() {
        Set<SelectTree> selectSet = new HashSet<>();

        List<SelectTree> placePointCategories = placePointService.findPlacePointCategories();
        List<SelectTree> placeAreaCategories = placeAreaService.findPlaceAreaCategories();

        selectSet.addAll(placePointCategories);
        selectSet.addAll(placeAreaCategories);
        return selectSet.stream().toList();
    }
}
