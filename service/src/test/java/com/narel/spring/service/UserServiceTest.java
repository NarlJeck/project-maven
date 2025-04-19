package com.narel.spring.service;

import com.narel.spring.config.IntegrationTestBase;
import com.narel.spring.dto.UserCreateEditDto;
import com.narel.spring.enums.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserServiceTest extends IntegrationTestBase {

    private final UserService userService;

    @Test
    void findAllUsersSuccessful() {


    }

    @Test
    void findUserByIdSuccessful() {


    }

    @Test
    void creatUserSuccessful() {

    }

    @Test
    void updateUserSuccessful() {

    }

    @Test
    void deleteUserSuccessful() {

    }

    private UserCreateEditDto getUserCreateEditDto() {
        return new UserCreateEditDto(
                "Luke Skywolker",
                8954852,
                "moonStation@gmail.com",
                "Tatuin",
                Role.USER,
                "bm2344",
                "fr3535",
                "2353 3553 3253 2353",
                "555"
        );
    }

    private UserCreateEditDto getUserCreateEditDtoTwo() {
        return new UserCreateEditDto(
                "Han Solo",
                8954852,
                "MillenniumFalcon@gmail.com",
                "Tatuin",
                Role.USER,
                "bm2344",
                "fr3535",
                "2353 3553 3253 2353",
                "555"
        );
    }

    private UserCreateEditDto getUserCreateEditDtoThree() {
        return new UserCreateEditDto(
                "Leia Skywolker",
                1233,
                "princess@gmail.com",
                "Organa",
                Role.USER,
                "sf333",
                "fr245",
                "2353 9901 3253 2353",
                "001"
        );
    }
}
