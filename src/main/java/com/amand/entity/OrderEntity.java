package com.amand.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity extends BaseEntity {

    @Column(name = "code_order")
    private String codeOrder;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private int price;

    @Column(name = "total_price")
    private int totalPrice;

    @Column
    private String email;

    @Column
    private String amount;

    @ManyToMany(mappedBy = "orders")
    private List<ProductEntity> products;

    @Column
    private Integer status;

}
