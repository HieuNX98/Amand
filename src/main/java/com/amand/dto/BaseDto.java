package com.amand.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDto<T> {
    private int id;

    private String createdBy;

    private Date createdDate;

    private String modifiedBy;

    private String modifiedDate;

}
