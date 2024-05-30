package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.mapservice.service.PlacePointService;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class PlacePointServiceImplTest {

    @Resource
    private PlacePointService placePointService;

    @Test
    void findBuildingByName() {
        //Assert.assertThrows(BusinessException.class, () -> placePointService.findPlacePointByName(""));
        System.out.println(Arrays.toString(placePointService.findPlacePointByName("").toArray()));
    }
}