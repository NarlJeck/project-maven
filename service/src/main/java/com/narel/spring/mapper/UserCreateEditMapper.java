package com.narel.spring.mapper;

import com.narel.spring.dto.UserCreateEditDto;
import com.narel.spring.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private  void copy(UserCreateEditDto object, User user) {
        user.setFullName(object.getFullName());
        user.setPhoneNumber(object.getPhoneNumber());
        user.setEmail(object.getEmail());
        user.setResidentialAddress(object.getResidentialAddress());
        user.setRole(object.getRole());
        user.setPassport(object.getPassport());
        user.setDriverLicense(object.getDriverLicense());
        user.setBankCard(object.getBankCard());
        user.setPassword(object.getPassword());

        Optional.ofNullable(object.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
    }
}
