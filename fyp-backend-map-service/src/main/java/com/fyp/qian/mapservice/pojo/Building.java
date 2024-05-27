package com.fyp.qian.mapservice.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName building
 */
@TableName(value ="building")
@Data
public class Building implements Serializable {
    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Object way;

    /**
     * 
     */
    private String building;

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
        Building other = (Building) that;
        return (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getWay() == null ? other.getWay() == null : this.getWay().equals(other.getWay()))
            && (this.getBuilding() == null ? other.getBuilding() == null : this.getBuilding().equals(other.getBuilding()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getWay() == null) ? 0 : getWay().hashCode());
        result = prime * result + ((getBuilding() == null) ? 0 : getBuilding().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", name=").append(name);
        sb.append(", way=").append(way);
        sb.append(", building=").append(building);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}