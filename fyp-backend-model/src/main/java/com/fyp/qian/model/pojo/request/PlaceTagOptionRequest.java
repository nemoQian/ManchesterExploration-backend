package com.fyp.qian.model.pojo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PlaceTagOptionRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private Long tagBelongs;

    private Long tagShownState;
}
