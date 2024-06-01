package com.fyp.qian.mapservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.PlacePoint;
import com.fyp.qian.mapservice.service.PlacePointService;
import com.fyp.qian.mapservice.mapper.PlacePointMapper;
import com.fyp.qian.model.pojo.SelectTree;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import com.fyp.qian.model.pojo.response.PlaceResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.fyp.qian.common.utils.OSMDataUtil.*;

/**
* @author Yihan Qian
* @description 针对表【place_point】的数据库操作Service实现
* @createDate 2024-05-29 17:12:37
*/
@Service
public class PlacePointServiceImpl extends ServiceImpl<PlacePointMapper, PlacePoint>
    implements PlacePointService{

    @Override
    public List<PlaceResponse> findPlacePoint(LocationSearchRequest locationSearchRequest) {
        String placeName = locationSearchRequest.getSearchName();
        String placeCategories = locationSearchRequest.getSearchCategories();
        String placeTag = locationSearchRequest.getSearchTag();
        QueryWrapper<PlacePoint> queryWrapper = new QueryWrapper<>();

        if(StringUtils.isNotBlank(placeName)){
            queryWrapper.apply("LOWER(name) LIKE LOWER({0})", "%" + placeName + "%");
        }

        if(StringUtils.isNotBlank(placeCategories)){
            queryWrapper.eq("amenity", placeCategories);
        }

        List<PlacePoint> points = this.list(queryWrapper);
        List<PlaceResponse> placeResponses = new ArrayList<>();

        for(PlacePoint point : points){
            String way = point.getWay().toString();
            PlaceResponse pointResponse = new PlaceResponse();
            pointResponse.setPlaceName(StringUtils.isBlank(point.getName()) ? placeCategories : point.getName());
            pointResponse.setLnglat(generateLatLon(way));
            pointResponse.setDescription(point.getTags());
            placeResponses.add(pointResponse);
        }
        return placeResponses;
    }

    @Override
    public List<SelectTree> findPlacePointCategories() {
        List<String> amenities = this.baseMapper.selectDistinctAmenity();
        List<SelectTree> selectTrees = new ArrayList<>();
        for(String amenity : amenities){
            SelectTree selectTree = new SelectTree();
            selectTree.setLabel(amenity);
            selectTree.setValue(amenity);
            selectTrees.add(selectTree);
        }
        return selectTrees;
    }
}




