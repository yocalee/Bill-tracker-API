package com.yocale.billmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BillDto extends BillRegisterDto{
    private long id;
    private UserRegisterDto user;
}
