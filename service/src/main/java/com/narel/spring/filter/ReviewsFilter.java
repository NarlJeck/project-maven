package com.narel.spring.filter;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReviewsFilter {

    String model;
    String brand;
}
