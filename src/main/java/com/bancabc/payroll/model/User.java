package com.bancabc.payroll.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean isActive;
    @CreationTimestamp
    private Date dateCreated;
    @ManyToOne
    @JoinColumn(name="roleId", nullable=false)
    private Role role;

    public User() {
    }

    public User(String firstName, String lastName, String userName, boolean isActive, Date dateCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.isActive = isActive;
        this.dateCreated = dateCreated;
    }

    public User(String firstName, String lastName, String userName, boolean isActive, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.isActive = isActive;
        this.role = role;
    }
}
