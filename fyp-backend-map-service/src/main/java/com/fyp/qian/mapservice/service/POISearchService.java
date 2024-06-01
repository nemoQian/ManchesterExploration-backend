package com.fyp.qian.mapservice.service;

import com.fyp.qian.model.pojo.SelectTree;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import com.fyp.qian.model.pojo.response.PlaceResponse;

import java.util.List;

public interface POISearchService {

    /**
     *
     * @param locationSearchRequest
     * @return
     */
    List<PlaceResponse> findPlace(LocationSearchRequest locationSearchRequest);

    /**
     *
     * @return
     */
    List<SelectTree> findPOICategories();
}
