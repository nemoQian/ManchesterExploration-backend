package com.fyp.qian.model.pojo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GroupSearchRequest extends PageRequest{

    private Long id;

    private String groupName;

    private String groupDescription;

    private Long groupCreateUserId;

    private Integer groupVisibility;
}
