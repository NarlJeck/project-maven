package com.narel.repository;

import com.narel.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<Integer, User> {

    public UserRepository() {
        super(User.class);
    }
}
