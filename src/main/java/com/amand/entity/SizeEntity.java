package com.amand.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "size")
public class SizeEntity extends BaseEntity {

    @Column
    private String name;

    public String getName() {
        return name;
    }

    @ManyToMany(mappedBy = "sizes")
    private List<ProductEntity> products;

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public void setName(String name) {
        this.name = name;
    }
}
