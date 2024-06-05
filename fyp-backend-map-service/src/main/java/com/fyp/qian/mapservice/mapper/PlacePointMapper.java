package com.fyp.qian.mapservice.mapper;

import com.fyp.qian.model.pojo.PlacePoint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Yihan Qian
* @description 针对表【place_point】的数据库操作Mapper
* @createDate 2024-05-29 17:12:37
* @Entity com.fyp.qian.model.pojo.PlacePoint
*/
public interface PlacePointMapper extends BaseMapper<PlacePoint> {

    @Select("SELECT DISTINCT amenity FROM place_point ORDER BY amenity")
    List<String> selectDistinctAmenity();

    @Select("SELECT osm_id, name, amenity, hstore_to_json(tags) as tags, way FROM PLACE_POINT")
    List<PlacePoint> selectAllWithJsonTags();
}




