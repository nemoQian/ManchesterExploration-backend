package com.fyp.qian.userservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.qian.common.common.BaseResponse;
import com.fyp.qian.common.common.ResponseResult;
import com.fyp.qian.model.pojo.SelectTree;
import com.fyp.qian.model.pojo.request.*;
import com.fyp.qian.userservice.service.PlaceTagsService;
import com.fyp.qian.userservice.service.PlaceTagsWaitingService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/placeTags")
public class PlaceTagsController {

    @Resource
    private PlaceTagsService placeTagsService;

    @Resource
    private PlaceTagsWaitingService placeTagsWaitingService;

    @GetMapping("/list")
    public BaseResponse<String> GetPlaceTagsList(HttpServletRequest request) {
        return ResponseResult.success(placeTagsService.placeTagsListShown(request));
    }

    @PostMapping("/addWaitingList")
    public BaseResponse<Long> AddWaitingList(@RequestBody TagWaitingListRequest tagWaitingListRequest, HttpServletRequest request) {
        return ResponseResult.success(placeTagsWaitingService.insertPlaceTagWaitingList(tagWaitingListRequest, request));
    }

    @PostMapping("/queryWaitingList")
    public BaseResponse<Page<PlaceTagWaitingListRequest>> GetWaitingList(@RequestBody PageRequest pageRequest, HttpServletRequest request) {
        return ResponseResult.success(placeTagsWaitingService.queryPlaceTagWaitingList(pageRequest, request));
    }

    @PostMapping("/queryGroupTagsOption")
    public BaseResponse<List<SelectTree>> GetGroupTagsOption(@RequestBody PlaceTagOptionRequest placeTagOptionRequest, HttpServletRequest request) {
        return ResponseResult.success(placeTagsService.queryPlaceTagOption(placeTagOptionRequest, request));
    }

    @PostMapping("/insertNewPlaceTag")
    public BaseResponse<Long> InsertNewPlaceTag(@RequestBody PlaceTagInsertRequest placeTagInsertRequest, HttpServletRequest request) {
        return ResponseResult.success(placeTagsService.InsertPlaceTag(placeTagInsertRequest, request));
    }

    @GetMapping("/deleteNewPlaceTag")
    public BaseResponse<Long> DeleteNewPlaceTag(@RequestParam("id") Long id){
        return ResponseResult.success(placeTagsWaitingService.deletePlaceTagWaitingList(id));
    }
}
