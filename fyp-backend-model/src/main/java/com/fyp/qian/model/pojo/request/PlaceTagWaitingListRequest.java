package com.fyp.qian.model.pojo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class PlaceTagWaitingListRequest extends PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private long id;

    private long osmId;

    private String placeName;

    private int tagVisibility;

    private long parentTagId;

    private long tagBelongsId;

    private Date createDate;
}
