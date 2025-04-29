package com.narel.spring.dto;

import com.narel.spring.entity.Car;
import com.narel.spring.entity.User;
import com.narel.spring.enums.OrderStatus;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;

@Value
@FieldNameConstants
public class OrderRentalReadDto {

    Integer id;
    UserReadDto user;
    CarReadDto car;
    Instant rentalStartTime;
    Instant rentalEndTime;
    Double totalRentalCost;
    OrderStatus orderStatus;
}
