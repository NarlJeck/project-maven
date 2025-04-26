package com.narel.spring.dto;

import com.narel.spring.enums.CarStatus;
import com.narel.spring.enums.Type;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Value
@FieldNameConstants
public class CarCreateEditDto {

    Integer year;
    String image;
    Double rentalPrice;
    String registrationNumber;
    String brand;
    String model;
    CarStatus status;
    Type type;
    String equipment;
    String fuelType;
    Double engineCapacity;
}
