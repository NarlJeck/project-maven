package com.narel.repository;

import com.narel.entity.User;
import jakarta.persistence.EntityManager;

public class UserRepository extends BaseRepository<Integer, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
