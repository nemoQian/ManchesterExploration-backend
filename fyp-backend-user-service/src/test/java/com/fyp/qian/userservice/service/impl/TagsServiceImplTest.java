package com.fyp.qian.userservice.service.impl;

import com.fyp.qian.userservice.service.TagsService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TagsServiceImplTest {

    @Resource
    private TagsService tagsService;

    @Test
    void getRootTagsList() {
        String tagsList = tagsService.getRootTagsList();
        System.out.println(tagsList);
    }

    @Test
    void getChildTags() {
        String tagsList = tagsService.getChildTags("gender");
        System.out.println(tagsList);
    }
}