package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class POISearchServiceImplTest {

    @Resource
    private POISearchServiceImpl poiSearchService;

    @Test
    void findPlace() {
        Assert.assertThrows(BusinessException.class, () -> poiSearchService.findPlace(new LocationSearchRequest()));
    }

    @Test
    void findPOICategories() {
        System.out.println(poiSearchService.findPOICategories().size());
    }
}