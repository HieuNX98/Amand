package com.amand.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class UserEntity extends BaseEntity {

    @Column(name = "username")
    private String userName;

    @Column
    private String password;

    @Column(name = "fullname")
    private String fullName;

    @Column
    private Date date;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private int status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;

    @OneToOne(mappedBy = "user")
    private BagEntity bag;
}
