package com.fyp.qian.mapservice.mapper;

import com.fyp.qian.model.pojo.PlaceArea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Yihan Qian
* @description 针对表【place_area】的数据库操作Mapper
* @createDate 2024-05-30 15:02:04
* @Entity com.fyp.qian.model.pojo.PlaceArea
*/
public interface PlaceAreaMapper extends BaseMapper<PlaceArea> {

    @Select("SELECT DISTINCT amenity FROM place_area ORDER BY amenity")
    List<String> selectDistinctAmenity();
}




