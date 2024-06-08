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
import com.fyp.qian.serviceclient.service.UserFeignClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.fyp.qian.common.utils.OSMDataUtil.*;

/**
* @author Yihan Qian
* @description 针对表【place_point】的数据库操作Service实现
* @createDate 2024-05-29 17:12:37
*/
@Service
public class PlacePointServiceImpl extends ServiceImpl<PlacePointMapper, PlacePoint>
    implements PlacePointService{

    @Resource
    private UserFeignClient userFeignClient;

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

        if(StringUtils.isNotBlank(placeTag)){
            List<Long> osmIds = userFeignClient.listPlaceTagIds(placeTag);
            queryWrapper.in("osm_id", osmIds);
        }

        List<PlacePoint> points = this.list(queryWrapper);
        List<PlaceResponse> placeResponses = new ArrayList<>();

        for(PlacePoint point : points){
            String way = point.getWay().toString();
            PlaceResponse pointResponse = new PlaceResponse();
            pointResponse.setOsmId(point.getOsmId());
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

    @Override
    public List<PlacePoint> selectPointByTagKey(String key) {
        List<PlacePoint> points = this.baseMapper.selectAllWithJsonTags();

        Gson gson = new Gson();
        return points.stream().filter(placePoint -> {
            String tags = placePoint.getTags().toString();
            if (StringUtils.isBlank(tags)) {
                return false;
            }
            Map<String, String> map = gson.fromJson(tags, Map.class);
            if (!map.containsKey(key)) {
                return false;
            }
            Map<String, String> tagMap = new HashMap<>();
            tagMap.put(key, map.get(key));
            placePoint.setTags(tagMap);
            return true;
        }).collect(Collectors.toList());
    }
}




