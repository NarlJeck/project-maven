package com.narel.spring.mapper;

import com.narel.spring.dto.CarReadDto;
import com.narel.spring.dto.OrderRentalReadDto;
import com.narel.spring.dto.UserReadDto;
import com.narel.spring.entity.OrderRental;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<OrderRental, OrderRentalReadDto> {

    private final UserReadMapper userReadMapper;
    private final CarReadMapper carReadMapper;

    @Override
    public OrderRentalReadDto map(OrderRental object) {
        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        CarReadDto car = Optional.ofNullable(object.getCar())
                .map(carReadMapper::map)
                .orElse(null);
        return new OrderRentalReadDto(
                object.getId(),
                user,
                car,
                object.getRentalStartTime(),
                object.getRentalEndTime(),
                object.getTotalRentalCost(),
                object.getStatus());

    }
}
