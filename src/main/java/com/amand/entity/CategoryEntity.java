package com.amand.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "category")
@Data
public class CategoryEntity extends BaseEntity{

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private Integer status;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;

}
