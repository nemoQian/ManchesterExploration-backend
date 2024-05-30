package com.fyp.qian.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName place_point
 */
@TableName(value ="place_point")
@Data
public class PlacePoint implements Serializable {
    /**
     * 
     */
    private Long osmId;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String amenity;

    /**
     * 
     */
    private Object tags;

    /**
     * 
     */
    private Object way;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PlacePoint other = (PlacePoint) that;
        return (this.getOsmId() == null ? other.getOsmId() == null : this.getOsmId().equals(other.getOsmId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAmenity() == null ? other.getAmenity() == null : this.getAmenity().equals(other.getAmenity()))
            && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()))
            && (this.getWay() == null ? other.getWay() == null : this.getWay().equals(other.getWay()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOsmId() == null) ? 0 : getOsmId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAmenity() == null) ? 0 : getAmenity().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        result = prime * result + ((getWay() == null) ? 0 : getWay().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", osmId=").append(osmId);
        sb.append(", name=").append(name);
        sb.append(", amenity=").append(amenity);
        sb.append(", tags=").append(tags);
        sb.append(", way=").append(way);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}