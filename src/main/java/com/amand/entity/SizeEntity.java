package com.amand.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "size")
@Data
public class SizeEntity extends BaseEntity {

    @Column
    private String name;



    @ManyToMany(mappedBy = "sizes")
    private List<ProductEntity> products;

    @Column
    private Integer status;

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }
}
