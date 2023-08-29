package com.amand.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "size")
@Data
public class SizeEntity extends BaseEntity {

    @Column
    private String name;

    public String getName() {
        return name;
    }

    @ManyToMany(mappedBy = "sizes")
    private List<ProductEntity> products;

}
