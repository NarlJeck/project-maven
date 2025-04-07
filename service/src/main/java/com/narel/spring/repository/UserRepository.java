package com.narel.spring.repository;

import com.narel.spring.entity.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Integer> {
}
