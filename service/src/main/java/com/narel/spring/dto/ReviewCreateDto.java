package com.narel.spring.dto;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;

@Value
@FieldNameConstants
@Builder
public class ReviewCreateDto {

    Integer carId;
    Integer userId;
    String reviewText;
    Integer rating;
    Instant createAd;
}
