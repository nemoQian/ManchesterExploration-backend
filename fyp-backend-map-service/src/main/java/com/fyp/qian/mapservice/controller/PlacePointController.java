package com.fyp.qian.mapservice.controller;

import com.fyp.qian.common.common.BaseResponse;
import com.fyp.qian.common.common.ResponseResult;
import com.fyp.qian.mapservice.service.PlacePointService;
import com.fyp.qian.model.pojo.response.PlaceResponse;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
public class PlacePointController {

    @Resource
    private PlacePointService placePointService;

    @PostMapping("/search")
    public BaseResponse<List<PlaceResponse>> search(@RequestBody LocationSearchRequest locationSearchRequest) {
        List<PlaceResponse> result = placePointService.findPlacePointByName(locationSearchRequest.getSearchName());
        return ResponseResult.success(result);
    }
}
