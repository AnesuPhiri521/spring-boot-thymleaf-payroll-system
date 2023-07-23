package com.bancabc.payroll.model;

import com.bancabc.payroll.utils.UserRoles;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(unique=true)
    private UserRoles name;
    @OneToMany(mappedBy="id")
    private List<User> user;

    public Role() {
    }

    public Role(UserRoles name) {
        this.name = name;
    }
}
