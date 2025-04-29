package com.narel.spring.dto;

import com.narel.spring.entity.Car;
import com.narel.spring.entity.User;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;

@Value
@FieldNameConstants
public class ReviewReadDto {

    Integer id;
    UserReadDto user;
    CarReadDto car;
    String reviewText;
    Integer rating;
    Instant createdAd;
}
