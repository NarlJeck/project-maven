package com.narel.spring.service;

import com.narel.spring.config.IntegrationTestBase;
import com.narel.spring.dto.UserCreateEditDto;
import com.narel.spring.dto.UserReadDto;
import com.narel.spring.enums.Role;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserServiceTest extends IntegrationTestBase {

    private final UserService userService;

    @Test
    void findAllUsersSuccessful() {
        userService.create(getUserCreateEditDto());
        userService.create(getUserCreateEditDtoThree());

        List<UserReadDto> result = userService.findAll();

        Assertions.assertThat(result).hasSize(2);
    }

    @Test
    void findUserByIdSuccessful() {
        UserCreateEditDto userCreate = getUserCreateEditDto();
        UserReadDto userReadDto = userService.create(userCreate);

        Optional<UserReadDto> maybeUser = userService.findById(userReadDto.getId());

        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(
                user -> {
                    assertEquals(userReadDto.getFullName(), user.getFullName());
                    assertEquals(userReadDto.getPassport(), user.getPassport());
                }
        );
    }

    @Test
    void creatUserSuccessful() {
        UserCreateEditDto userDto = getUserCreateEditDto();

        UserReadDto actualResult = userService.create(userDto);

        assertEquals(userDto.getFullName(), actualResult.getFullName());
        assertEquals(userDto.getPhoneNumber(), actualResult.getPhoneNumber());
        assertEquals(userDto.getPassport(), actualResult.getPassport());
    }

    @Test
    void updateUserSuccessful() {
        UserCreateEditDto userOriginalDto = getUserCreateEditDto();
        UserCreateEditDto userUpdateDto = getUserCreateEditDtoTwo();
        UserReadDto userDto = userService.create(userOriginalDto);

        Optional<UserReadDto> userActual = userService.update(userDto.getId(), userUpdateDto);

        assertTrue(userActual.isPresent());
        userActual.ifPresent(
                user -> {
                    assertNotEquals(userDto.getFullName(), user.getFullName());
                    assertNotEquals(userDto.getEmail(), user.getEmail());
                }
        );
    }

    @Test
    void deleteUserSuccessful() {
        UserCreateEditDto userCreate = getUserCreateEditDto();
        UserReadDto userReadDto = userService.create(userCreate);

        boolean deleteActualResult = userService.delete(userReadDto.getId());
        boolean deleteIrrelevantResult = userService.delete(1020001010);

        assertTrue(deleteActualResult);
        assertFalse(deleteIrrelevantResult);

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
