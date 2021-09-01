package com.yocale.billmanagement.controllers;

import com.yocale.billmanagement.dtos.StatisticDto;
import com.yocale.billmanagement.entities.BillType;
import com.yocale.billmanagement.entities.TimeType;
import com.yocale.billmanagement.security.model.UserPrincipal;
import com.yocale.billmanagement.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService service;

    @Autowired
    public StatisticController(StatisticService service) {
        this.service = service;
    }

    @GetMapping("/{time}/{billType}")
    public ResponseEntity<StatisticDto> getStatistic(@PathVariable TimeType time, @PathVariable BillType billType, @AuthenticationPrincipal UserPrincipal user) {
        StatisticDto statistic = service.getStatistic(time, billType, user);
        return ResponseEntity.ok(statistic);
    }

}