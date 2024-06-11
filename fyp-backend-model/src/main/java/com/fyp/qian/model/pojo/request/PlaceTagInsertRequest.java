package com.fyp.qian.model.pojo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PlaceTagInsertRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private Long osmId;

    private String tagName;

    private Long tagParentId;

    private Integer tagVisibility;

    private Long tagBelongs;
}
