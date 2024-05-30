package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.common.common.exception.BusinessException;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class POISearchServiceImplTest {

    @Resource
    private POISearchServiceImpl poiSearchService;

    @Test
    void findPlaceByName() {
        Assert.assertThrows(BusinessException.class, () -> poiSearchService.findPlaceByName(""));
    }
}