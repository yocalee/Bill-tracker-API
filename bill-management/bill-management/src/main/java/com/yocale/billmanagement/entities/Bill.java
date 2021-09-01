package com.yocale.billmanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Bill extends BaseEntity{
    @Enumerated(value = EnumType.STRING)
    private BillType category;
    private double price;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate date;

}
