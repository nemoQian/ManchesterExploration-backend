package com.fyp.qian.mapservice.service;

import com.fyp.qian.mapservice.pojo.Building;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.qian.mapservice.pojo.BuildingResponse;

import java.util.List;

/**
* @author Yihan Qian
* @description 针对表【building】的数据库操作Service
* @createDate 2024-05-27 16:49:27
*/
public interface BuildingService extends IService<Building> {
    List<BuildingResponse> findBuildingByName(String buildingName);
}
