package com.amand.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "role")
@Data
public class RoleEntity extends BaseEntity{

    @Column
    private String name;

    @Column
    private String code;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;

}
