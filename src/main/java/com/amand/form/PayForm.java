package com.amand.form;

import lombok.Data;

@Data
public class PayForm extends BaseForm{
    private String select;

    private String sinceDate;

    private String toDate;

    private long day;
}
