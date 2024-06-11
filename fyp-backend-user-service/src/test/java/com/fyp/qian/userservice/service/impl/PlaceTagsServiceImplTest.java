package com.fyp.qian.userservice.service.impl;

import com.fyp.qian.model.pojo.PlaceTags;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaceTagsServiceImplTest {

    @Resource
    private PlaceTagsServiceImpl placeTagsService;

//    @Test
//    void insertPlaceTag() {
//        PlaceTags placeTags = new PlaceTags();
//        placeTags.setOsmId(0L);
//        placeTags.setTagName("cuisine");
//        placeTags.setTagShownStatus(0);
//        placeTags.setTagBelongs(-1L);
//        placeTagsService.InsertPlaceTag(placeTags, null);
//    }

    @Test
    void placeTagsListShown() {
        System.out.println(placeTagsService.placeTagsListShown(null));
    }
}