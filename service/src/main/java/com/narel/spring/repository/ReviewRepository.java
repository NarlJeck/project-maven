package com.narel.spring.repository;

import com.narel.spring.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReviewRepository extends JpaRepository<Review, Integer>,
        FilterReviewRepository,
        QuerydslPredicateExecutor<Review> {
}
