package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.mapservice.service.BuildingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class BuildingServiceImplTest {

    @Autowired
    private BuildingService buildingService;

    @Test
    void findBuildingByName() {
        System.out.println(Arrays.toString(buildingService.findBuildingByName("man").toArray()));
    }
}