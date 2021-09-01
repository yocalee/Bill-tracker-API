package com.yocale.billmanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "users")
public class User extends BaseEntity{
    private String username;
    private String password;
    @Column(name = "is_admin")
    private boolean admin;
    private double budget;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Bill> bills ;

}
