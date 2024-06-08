package com.fyp.qian.userservice.controller;

import com.fyp.qian.common.common.BaseResponse;
import com.fyp.qian.common.common.ResponseResult;
import com.fyp.qian.model.pojo.request.TagWaitingListRequest;
import com.fyp.qian.userservice.service.PlaceTagsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/placeTags")
public class PlaceTagsController {

    @Resource
    private PlaceTagsService placeTagsService;

    @GetMapping("/list")
    public BaseResponse<String> GetPlaceTagsList(){
        return ResponseResult.success(placeTagsService.placeTagsListShown(null));
    }

    @PostMapping("/addWaitingList")
    public BaseResponse<Long> AddWaitingList(@RequestBody TagWaitingListRequest tagWaitingListRequest, HttpServletRequest request){
        return ResponseResult.success(placeTagsService.InsertPlaceTagWaitingList(tagWaitingListRequest, request));
    }
}
