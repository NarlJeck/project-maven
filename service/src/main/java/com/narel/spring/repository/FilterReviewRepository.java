package com.narel.spring.repository;

import com.narel.spring.entity.Review;
import com.narel.spring.filter.ReviewsFilter;

import java.util.List;

public interface FilterReviewRepository {

    List<Review> findAllByFilter(ReviewsFilter filter);
}
