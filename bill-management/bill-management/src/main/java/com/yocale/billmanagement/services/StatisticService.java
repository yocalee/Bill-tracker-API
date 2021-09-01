package com.yocale.billmanagement.services;

import com.yocale.billmanagement.dtos.BillRegisterDto;
import com.yocale.billmanagement.dtos.StatisticDto;
import com.yocale.billmanagement.entities.BillType;
import com.yocale.billmanagement.entities.TimeType;
import com.yocale.billmanagement.entities.User;
import com.yocale.billmanagement.repositories.UserRepository;
import com.yocale.billmanagement.security.model.UserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public StatisticService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public StatisticDto getStatistic(TimeType time, BillType billType, UserPrincipal principal) {
        StatisticDto statisticDto;
        User user = userRepository.findByUsername(principal.getUsername()).get();
        switch (time.name().toUpperCase()) {
            case "WEEK":
                statisticDto = getWeeklyStatistic(user, billType);
                break;
            case "MONTH":
                statisticDto = getMonthlyStatistic(user, billType);
                break;
            case "YEAR":
                statisticDto = getYearlyStatistic(user, billType);
                break;
            default:
                throw new InvalidParameterException("Parameter time is invalid. Valid time parameters are: WEEK, MONTH, YEAR.");
        }
        return statisticDto;
    }

    private StatisticDto getYearlyStatistic(User user, BillType category) {
        StatisticDto dto = new StatisticDto();
        List<BillRegisterDto> bills = user.getBills()
                .stream()
                .filter(b -> b.getCategory().equals(category))
                .filter(b -> b.getDate().getYear() == LocalDate.now().getYear())
                .map(b -> mapper.map(b, BillRegisterDto.class))
                .collect(Collectors.toList());
        dto.setBills(bills);

        double cost = bills.stream().map(BillRegisterDto::getPrice).reduce(Double::sum).get();
        dto.setCostOfPaidBills(cost);

        double yearlyBudget = user.getBudget()*12;
        double percentage = Math.ceil( (cost / yearlyBudget) * 100) ;

        dto.setPercentageOfSalaryPerPeriod(percentage);
        dto.setStartDate(bills.get(0).getDate());
        dto.setEndDate(bills.get(bills.size()-1).getDate());
        return dto;
    }

    private StatisticDto getMonthlyStatistic(User user, BillType billType) {
        return null;
    }

    private StatisticDto getWeeklyStatistic(User user, BillType billType) {
        return null;
    }
}
