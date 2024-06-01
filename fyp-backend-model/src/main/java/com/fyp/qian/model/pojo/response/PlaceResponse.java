package com.fyp.qian.model.pojo.response;

import lombok.Data;

@Data
public class PlaceResponse {

    private String placeName;

    private double[] lnglat;

    private Object description;
}
