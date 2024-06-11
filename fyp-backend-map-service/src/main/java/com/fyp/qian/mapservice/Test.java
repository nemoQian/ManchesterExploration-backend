package com.fyp.qian.mapservice;

import com.fyp.qian.model.pojo.PlaceTags;
import com.fyp.qian.serviceclient.service.UserFeignClient;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/place")
public class Test {

    @Resource
    private UserFeignClient userFeignClient;

//    @GetMapping("/in")
//    public void in() {
//        PlaceTags placeTags = new PlaceTags();
//        placeTags.setOsmId(0L);
//        placeTags.setTagName("wheelchair");
//        placeTags.setTagShownStatus(0);
//        placeTags.setTagBelongs(-1L);
//        userFeignClient.insertPointTag(placeTags);
//    }
}
