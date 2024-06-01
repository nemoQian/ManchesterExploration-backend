package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.mapservice.service.PlacePointService;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class PlacePointServiceImplTest {

    @Resource
    private PlacePointService placePointService;

    @Test
    void findBuildingByName() {
        //Assert.assertThrows(BusinessException.class, () -> placePointService.findPlacePoint(""));
        System.out.println(Arrays.toString(placePointService.findPlacePoint(new LocationSearchRequest()).toArray()));
    }

    @Test
    void findPlacePointCategories() {
        System.out.println(placePointService.findPlacePointCategories());
    }
}