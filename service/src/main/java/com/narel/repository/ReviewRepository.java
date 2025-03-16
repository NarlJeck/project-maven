package com.narel.repository;

import com.narel.entity.Review;
import jakarta.persistence.EntityManager;

public class ReviewRepository extends BaseRepository<Integer,Review>{

    public ReviewRepository(EntityManager entityManager) {
        super(Review.class, entityManager);
    }
}
