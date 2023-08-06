package com.amand.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Data
public class ProductEntity extends BaseEntity {

    @Column
    private String name;

    @Column(name = "oldprice")
    private int oldPrice;

    @Column(name = "saleprice")
    private int salePrice;

    @Column
    private int amount;

    @Column
    private String season;

    @ManyToMany
    @JoinTable(name = "product_size",
               joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<SizeEntity> sizes ;

    @ManyToMany
    @JoinTable(name = "product_color",
                joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "color_id"))
    private List<ColorEntity> colors;

    @ManyToMany
    @JoinTable(name = "product_bag",
                joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "bag_id"))
    private List<BagEntity> bags;

    @ManyToMany
    @JoinTable(name = "product_order",
                joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<OrderEntity> orders;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "products")
    private List<ImageEntity> images;

}
