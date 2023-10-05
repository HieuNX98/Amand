package com.amand.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "pay")
@Data
public class PayEntity extends BaseEntity{


    @Column
    //Mã giao dịch
    private String vnp_TxnRef;

    @Column
    private String time;

    @Column
    //Thông tin giao dịch
    private String vnp_OrderInfo;

    @Column
    //Số tiền thanh toán
    private Double vnp_Amount;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
