package com.yocale.billmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserDto extends UserRegisterDto {
    private long id;
    private List<BillRegisterDto> bills;
}
