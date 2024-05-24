package com.fyp.qian.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.qian.model.pojo.Tags;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Yihan Qian
* @description 针对表【tags(tag table)】的数据库操作Service
* @createDate 2024-04-28 16:34:35
*/
public interface TagsService extends IService<Tags> {

    /**
     *
     * @param parentTagName
     * @param tagName
     * @param available
     * @param request
     * @return
     */
    int insertTags(String parentTagName, String tagName, int available, HttpServletRequest request);

    String getRootTagsList();

    String getChildTags(String parentTagName);
}
