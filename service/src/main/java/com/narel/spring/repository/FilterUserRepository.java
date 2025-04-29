package com.narel.spring.repository;

import com.narel.spring.entity.User;
import com.narel.spring.filter.UserFilter;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter filter);
}
