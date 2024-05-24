package com.fyp.qian.userservice.controller;

import com.fyp.qian.common.common.BaseResponse;
import com.fyp.qian.common.common.ResponseResult;
import com.fyp.qian.model.pojo.request.TagAddRequest;
import com.fyp.qian.userservice.service.TagsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Resource
    private TagsService tagsService;

    @PostMapping("/newTag")
    public BaseResponse<Integer> newTag(@RequestBody TagAddRequest tagAddRequest, HttpServletRequest request) {
        String parentTagName = tagAddRequest.getParentTagName();
        String tagName = tagAddRequest.getTagName();
        int available = tagAddRequest.getAvailable();

        int result = tagsService.insertTags(parentTagName, tagName, available, request);
        return ResponseResult.success(result);
    }

    @GetMapping("/rootTags")
    public BaseResponse<String> getRootTags() {
        return ResponseResult.success(tagsService.getRootTagsList());
    }

    @PostMapping("/childTags")
    public BaseResponse<String> getChildTags(@RequestBody String tagName) {
        return ResponseResult.success(tagsService.getChildTags(tagName));
    }
}
