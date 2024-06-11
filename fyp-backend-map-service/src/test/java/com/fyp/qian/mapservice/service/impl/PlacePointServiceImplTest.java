package com.fyp.qian.mapservice.service.impl;

import com.fyp.qian.mapservice.service.PlacePointService;
import com.fyp.qian.model.pojo.PlacePoint;
import com.fyp.qian.model.pojo.PlaceTags;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import com.fyp.qian.serviceclient.service.UserFeignClient;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class PlacePointServiceImplTest {

    @Resource
    private PlacePointService placePointService;

    @Resource
    private UserFeignClient userFeignClient;

    @Test
    void findBuildingByName() {
        //Assert.assertThrows(BusinessException.class, () -> placePointService.findPlacePoint(""));
        System.out.println(Arrays.toString(placePointService.findPlacePoint(new LocationSearchRequest()).toArray()));
    }

    @Test
    void findPlacePointCategories() {
        System.out.println(placePointService.findPlacePointCategories());
    }

//    @Test
//    void selectPointByTagKey() {
////        System.out.println(placePointService.selectPointByTagKey("wheelchair"));
//        String key = "wheelchair";
//        List<PlacePoint> l = placePointService.selectPointByTagKey(key);
//        List<PlaceTags> l2 = new ArrayList<>();
//        for(PlacePoint p : l){
//            Map<String , String > tagMap = (Map<String, String>) p.getTags();
//            String value = tagMap.get(key);
//            String[] values = value.split(";");
//            for(String v : values){
//                PlaceTags placeTags = new PlaceTags();
//                placeTags.setOsmId(p.getOsmId());
//                placeTags.setTagName(v);
//                placeTags.setTagShownStatus(0);
//                placeTags.setTagParentId(3L);
//                placeTags.setTagBelongs(-1L);
//                l2.add(placeTags);
////                System.out.println(placeTags);
//                userFeignClient.insertPointTag(placeTags);
//            }
//        }
//        System.out.println(l2.size());
//    }
}