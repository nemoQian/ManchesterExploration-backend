package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.mapservice.service.PlacePointService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class BuildingServiceImplTest {

    @Resource
    private PlacePointService placePointService;

    @Test
    void findBuildingByName() {
        System.out.println(Arrays.toString(placePointService.findPlacePointByName("man").toArray()));
    }
}