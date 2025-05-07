package com.narel.spring.dto;

import com.narel.spring.enums.OrderStatus;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@FieldNameConstants
public class OrderRentalCreateDto {

    Integer userId;
    Integer carId;
    Instant rentalStartTime;
    Instant rentalEndTime;
    BigDecimal totalRentalCost;
    OrderStatus orderStatus;
}
