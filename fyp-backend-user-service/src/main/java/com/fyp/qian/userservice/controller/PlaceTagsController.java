package com.fyp.qian.userservice.controller;

import com.fyp.qian.common.common.BaseResponse;
import com.fyp.qian.common.common.ResponseResult;
import com.fyp.qian.userservice.service.PlaceTagsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/placeTags")
public class PlaceTagsController {

    @Resource
    private PlaceTagsService placeTagsService;

    @GetMapping("list")
    public BaseResponse<String> GetPlaceTagsList(){
        return ResponseResult.success(placeTagsService.placeTagsListShown(null));
    }
}
