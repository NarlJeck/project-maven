package com.narel.spring.filter;

import com.narel.spring.enums.CarStatus;
import com.narel.spring.enums.Type;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarFilter {

    String brand;
    String fuelType;
    Integer year;
    Double rentalPrice;
    Type type;
    Double engineCapacity;
}
