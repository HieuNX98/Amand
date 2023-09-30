package com.amand.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product_oders")
@Data
public class ProductOrderEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column
    private Integer amount;

    @Column
    private String sizeName;

    @Column
    private String colorName;


}
