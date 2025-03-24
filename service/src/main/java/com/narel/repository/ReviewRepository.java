package com.narel.repository;

import com.narel.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository extends BaseRepository<Integer, Review> {

    public ReviewRepository() {
        super(Review.class);
    }
}
