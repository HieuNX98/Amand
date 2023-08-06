package com.amand.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "color")
@Data
public class ColorEntity extends BaseEntity {

    @Column
    private String name;

    @ManyToMany(mappedBy = "colors")
    private List<ProductEntity> products;

}
