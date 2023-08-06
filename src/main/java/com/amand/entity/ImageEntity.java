package com.amand.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Data
public class ImageEntity extends BaseEntity {

    @Column
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity products;

}
