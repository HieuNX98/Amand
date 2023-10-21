package com.amand.dto;

import lombok.Data;

@Data
public class PayDto extends BaseDto{

    private String time;

    private double money;


    public PayDto(String time, double money) {
        this.time = time;
        this.money = money;
    }

    public PayDto() {
    }

}
