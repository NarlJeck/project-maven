package com.narel.spring.repository;

import com.narel.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>,
        FilterUserRepository,
        QuerydslPredicateExecutor<User> {

    Optional<User> findByEmail(String email);
}
