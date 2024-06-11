package com.fyp.qian.userservice.controller.inner;

import com.fyp.qian.model.pojo.PlaceTags;
import com.fyp.qian.model.pojo.User;
import com.fyp.qian.serviceclient.service.UserFeignClient;
import com.fyp.qian.userservice.service.PlaceTagsService;
import com.fyp.qian.userservice.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;

    @Resource
    private PlaceTagsService placeTagsService;

    @Override
    @GetMapping("/get/id")
    public User getUserById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }

    @Override
    @GetMapping("/get/ids")
    public List<User> listUserByIds(@RequestParam("ids") Collection<Long> ids) {
        return userService.listByIds(ids);
    }

//    @Override
//    @PostMapping("/insertPointTags")
//    public void insertPointTag(@RequestBody PlaceTags placeTags) {
//        placeTagsService.InsertPlaceTag(placeTags, null);
//    }

    @GetMapping("/get/placeTagIds")
    @Override
    public List<Long> listPlaceTagIds(@RequestParam("tagName") String tagName) {
        return placeTagsService.searchPlaceByTag(tagName);
    }
}
