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

    @Column(name = "codeorder")
    private String codeOrder;

    @Column(name = "fullname")
    private String fullName;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private int price;

    @Column(name = "totalprice")
    private int totalPrice;

    @Column
    private String email;

    @Column
    private String amount;

    @Column
    private String status;

    @ManyToMany(mappedBy = "orders")
    private List<ProductEntity> products;

}
