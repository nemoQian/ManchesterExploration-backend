package com.fyp.qian.mapservice.controller;

import com.fyp.qian.common.common.BaseResponse;
import com.fyp.qian.common.common.ResponseResult;
import com.fyp.qian.mapservice.service.POISearchService;
import com.fyp.qian.model.pojo.SelectTree;
import com.fyp.qian.model.pojo.response.PlaceResponse;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
public class POISearchController {

    @Resource
    private POISearchService poiSearchService;

    @PostMapping("/search")
    public BaseResponse<List<PlaceResponse>> searchPOI(@RequestBody LocationSearchRequest locationSearchRequest) {
        List<PlaceResponse> result = poiSearchService.findPlace(locationSearchRequest);
        return ResponseResult.success(result);
    }

    @GetMapping("/categoriesList")
    public BaseResponse<List<SelectTree>> getCategoriesList(){
        return ResponseResult.success(poiSearchService.findPOICategories());
    }
}
