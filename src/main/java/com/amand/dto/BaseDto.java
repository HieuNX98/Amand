package com.amand.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BaseDto<T> {
    private int id;

    private String createdBy;

    private Date createdDate;

    private String modifiedBy;

    private String modifiedDate;

}
