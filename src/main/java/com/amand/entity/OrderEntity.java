package com.amand.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity extends BaseEntity {

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<ProductOrderEntity> productOrder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "code_order")
    private String codeOrder;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private String fullName;

    @Column
    private String phone;

    @Column
    private String note;

    @Column
    private Double subtotal;

    @Column
    private Double transportFee;

    @Column(name = "total_price")
    private Double totalPrice;

}
