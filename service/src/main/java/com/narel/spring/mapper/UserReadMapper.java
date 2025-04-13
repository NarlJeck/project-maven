package com.narel.spring.mapper;

import com.narel.spring.dto.UserReadDto;
import com.narel.spring.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getFullName(),
                object.getPhoneNumber(),
                object.getEmail(),
                object.getResidentialAddress(),
                object.getRole(),
                object.getPassport(),
                object.getDriverLicense(),
                object.getBankCard(),
                object.getPassword());

    }
}
