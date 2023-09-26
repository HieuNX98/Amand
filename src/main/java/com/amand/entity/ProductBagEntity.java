package com.amand.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product_bag")
@Data
public class ProductBagEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "bag_id")
    private BagEntity bag;

    @Column
    private String color_name;

    @Column
    private String size_name;

    @Column
    private Integer amount;
}
