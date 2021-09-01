package com.yocale.billmanagement.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StatisticDto {
    private double percentageOfSalaryPerPeriod;
    private double costOfPaidBills;
    private List<BillRegisterDto> bills;
    private LocalDate startDate;
    private LocalDate endDate;
}
