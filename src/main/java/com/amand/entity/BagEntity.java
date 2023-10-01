package com.amand.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bag")
@Data
public class BagEntity extends BaseEntity {

    @Column
    private Integer amount;

    @OneToMany(mappedBy = "bag")
    private List<ProductBagEntity> productBags ;

    @OneToOne
    @JoinColumn(name = "User_id")
    private UserEntity user;

}
