package com.fyp.qian.mapservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.PlaceArea;
import com.fyp.qian.mapservice.service.PlaceAreaService;
import com.fyp.qian.mapservice.mapper.PlaceAreaMapper;
import com.fyp.qian.model.pojo.SelectTree;
import com.fyp.qian.model.pojo.request.LocationSearchRequest;
import com.fyp.qian.model.pojo.response.PlaceResponse;
import com.fyp.qian.serviceclient.service.UserFeignClient;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.fyp.qian.common.utils.OSMDataUtil.generateLatLon;

/**
* @author Yihan Qian
* @description 针对表【place_area】的数据库操作Service实现
* @createDate 2024-05-30 15:02:04
*/
@Service
public class PlaceAreaServiceImpl extends ServiceImpl<PlaceAreaMapper, PlaceArea>
    implements PlaceAreaService{

    @Resource
    private UserFeignClient userFeignClient;

    @Override
    public List<PlaceResponse> findPlaceArea(LocationSearchRequest locationSearchRequest) {
        String placeName = locationSearchRequest.getSearchName();
        String placeCategories = locationSearchRequest.getSearchCategories();
        String placeTag = locationSearchRequest.getSearchTag();
        QueryWrapper<PlaceArea> queryWrapper = new QueryWrapper<>();

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

        List<PlaceArea> areas = this.list(queryWrapper);
        List<PlaceResponse> placeResponses = new ArrayList<>();

        for(PlaceArea area : areas){
            String way = area.getWay().toString();
            PlaceResponse buildingResponse = new PlaceResponse();
            buildingResponse.setOsmId(area.getOsmId());
            buildingResponse.setPlaceName(StringUtils.isBlank(area.getName()) ? placeCategories : area.getName());
            buildingResponse.setLnglat(generateLatLon(way));
            buildingResponse.setDescription(area.getTags());
            placeResponses.add(buildingResponse);
        }
        return placeResponses;
    }

    @Override
    public List<SelectTree> findPlaceAreaCategories() {
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




