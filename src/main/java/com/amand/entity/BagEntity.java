package com.amand.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bag")
@Data
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

}
