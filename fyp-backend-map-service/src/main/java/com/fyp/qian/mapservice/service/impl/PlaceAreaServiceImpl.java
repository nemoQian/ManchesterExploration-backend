package com.fyp.qian.mapservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.PlaceArea;
import com.fyp.qian.mapservice.service.PlaceAreaService;
import com.fyp.qian.mapservice.mapper.PlaceAreaMapper;
import com.fyp.qian.model.pojo.response.PlaceResponse;
import io.micrometer.common.util.StringUtils;
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

    @Override
    public List<PlaceResponse> findPlaceAreaByName(String placeName) {
        if(StringUtils.isBlank(placeName)){
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR, "Please input search name.");
        }
        QueryWrapper<PlaceArea> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("LOWER(name) LIKE LOWER({0})", "%" + placeName + "%");
        List<PlaceArea> areas = this.list(queryWrapper);
        List<PlaceResponse> placeResponses = new ArrayList<>();

        for(PlaceArea area : areas){
            String way = area.getWay().toString();
            PlaceResponse buildingResponse = new PlaceResponse();
            buildingResponse.setPlaceName(area.getName());
            buildingResponse.setLnglat(generateLatLon(way));
            placeResponses.add(buildingResponse);
        }
        return placeResponses;
    }
}




