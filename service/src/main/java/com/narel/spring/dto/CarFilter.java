package com.narel.spring.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarFilter {

    String brand;
    Integer year;
    String fuelType;
    Double engineCapacity;
}
