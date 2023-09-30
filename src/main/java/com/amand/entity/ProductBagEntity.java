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

    @Column(name = "color_name")
    private String colorName;

    @Column(name = "size_name")
    private String sizeName;

    @Column
    private Integer amount;
}
