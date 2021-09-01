package com.yocale.billmanagement.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserRegisterDto {
    private String username;
    private String password;
    private boolean admin;
    private double budget;
}
