package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.mapservice.service.PlaceAreaService;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlaceAreaServiceImplTest {

    @Resource
    private PlaceAreaService placeAreaService;

    @Test
    void findPlacePointByName() {
        System.out.println(placeAreaService.findPlaceArea(new LocationSearchRequest()));
    }

    @Test
    void findPlaceAreaCategories() {
        System.out.println(placeAreaService.findPlaceAreaCategories());
    }
}