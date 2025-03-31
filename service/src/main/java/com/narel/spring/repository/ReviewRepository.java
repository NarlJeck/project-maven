package com.narel.spring.repository;

import com.narel.spring.entity.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository extends BaseRepository<Integer, Review> {

    public ReviewRepository() {
        super(Review.class);
    }
}
