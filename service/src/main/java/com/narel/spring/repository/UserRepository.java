package com.narel.spring.repository;

import com.narel.spring.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<Integer, User> {

    public UserRepository() {
        super(User.class);
    }
}
