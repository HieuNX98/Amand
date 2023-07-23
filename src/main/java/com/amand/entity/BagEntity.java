package com.amand.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bag")
public class BagEntity extends BaseEntity {

    @Column
    private String size;

    @Column
    private String color;

    @Column
    private int amount;

    @ManyToMany(mappedBy = "bags")
    private List<ProductEntity> products ;

    @OneToOne
    @JoinColumn(name = "User_id")
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setBags(List<ProductEntity> products) {
        this.products = products;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
