package com.fyp.qian.mapservice.controller;

import com.fyp.qian.common.common.BaseResponse;
import com.fyp.qian.common.common.ResponseResult;
import com.fyp.qian.mapservice.pojo.Building;
import com.fyp.qian.mapservice.pojo.BuildingResponse;
import com.fyp.qian.mapservice.service.BuildingService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/building")
public class BuildingController {

    @Resource
    private BuildingService buildingService;

    @PostMapping("/search")
    public BaseResponse<List<BuildingResponse>> search(@RequestBody String buildingName) {
        List<BuildingResponse> result = buildingService.findBuildingByName(buildingName);
        return ResponseResult.success(result);
    }
}
