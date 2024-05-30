package com.fyp.qian.mapservice.service;

import com.fyp.qian.model.pojo.response.PlaceResponse;

import java.util.List;

public interface POISearchService {

    /**
     *
     * @param placeName
     * @return
     */
    List<PlaceResponse> findPlaceByName(String placeName);
}
