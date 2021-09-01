package com.yocale.billmanagement.dtos;

import com.yocale.billmanagement.entities.BillType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BillRegisterDto {
    private BillType category;
    private double price;
    private LocalDate date;
}
