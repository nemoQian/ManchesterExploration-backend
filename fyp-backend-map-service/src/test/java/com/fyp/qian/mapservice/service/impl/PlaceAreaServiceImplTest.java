package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.mapservice.service.PlaceAreaService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaceAreaServiceImplTest {

    @Resource
    private PlaceAreaService placeAreaService;

    @Test
    void findPlacePointByName() {
        System.out.println(placeAreaService.findPlaceAreaByName("university"));
    }
}