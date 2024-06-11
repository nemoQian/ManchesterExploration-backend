package com.fyp.qian.userservice.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fyp.qian.model.pojo.PlaceTags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Yihan Qian
* @description 针对表【place_tags(tag table)】的数据库操作Mapper
* @createDate 2024-06-05 14:08:32
* @Entity com.fyp.qian.model.pojo.PlaceTags
*/
public interface PlaceTagsMapper extends BaseMapper<PlaceTags> {

    @Select("SELECT DISTINCT id, tag_name FROM place_tags WHERE tag_parent_id = #{tagParentId} " +
            "AND tag_shown_status = #{tagShownState} AND approval_state = #{approvalState} " +
            "ORDER BY tag_name")
    List<PlaceTags> selectDistinctTagsWithId(@Param("tagParentId") long tagParentId,
                                       @Param("tagShownState") int tagShownState,
                                       @Param("approvalState") int approvalState);

    @Select("SELECT DISTINCT tag_name FROM place_tags WHERE tag_parent_id = #{tagParentId} " +
            "AND tag_shown_status = #{tagShownState} AND approval_state = #{approvalState} " +
            "ORDER BY tag_name")
    List<PlaceTags> selectDistinctTagsWithoutId(@Param("tagParentId") long tagParentId,
                                             @Param("tagShownState") int tagShownState,
                                             @Param("approvalState") int approvalState);

    @Select("SELECT DISTINCT id, tag_name FROM place_tags WHERE tag_parent_id = #{tagParentId} " +
            "AND tag_belongs = #{tagBelongs} AND tag_shown_status = #{tagShownState} " +
            "AND approval_state = #{approvalState} " +
            "ORDER BY tag_name")
    List<PlaceTags> selectDistinctTagsByTagBelongs(@Param("tagBelongs") long tagBelongs,
                                                   @Param("tagParentId") long tagParentId,
                                                   @Param("tagShownState") int tagShownState,
                                                   @Param("approvalState") int approvalState);
}




