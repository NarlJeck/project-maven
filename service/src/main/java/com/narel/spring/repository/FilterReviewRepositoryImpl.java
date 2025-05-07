package com.narel.spring.repository;

import com.narel.spring.entity.Review;
import com.narel.spring.filter.QPredicate;
import com.narel.spring.filter.ReviewsFilter;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.narel.spring.entity.QReview.review;

@RequiredArgsConstructor
public class FilterReviewRepositoryImpl implements FilterReviewRepository{

    private final EntityManager entityManager;
    @Override
    public List<Review> findAllByFilter(ReviewsFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getBrand(), review.car.brand::containsIgnoreCase)
                .add(filter.getModel(), review.car.model::containsIgnoreCase)
                .buildAnd();

        return new JPAQuery<Review>(entityManager)
                .select(review)
                .from(review)
                .join(review.car)
                .where(predicate)
                .fetch();
    }
}
