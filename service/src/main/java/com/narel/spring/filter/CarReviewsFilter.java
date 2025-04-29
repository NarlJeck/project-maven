package com.narel.spring.filter;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarReviewsFilter {

    String model;
    String brand;
}
