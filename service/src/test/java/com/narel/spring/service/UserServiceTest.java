package com.narel.spring.service;

import com.narel.spring.dto.UserCreateEditDto;
import com.narel.spring.dto.UserReadDto;
import com.narel.spring.entity.User;
import com.narel.spring.enums.Role;
import com.narel.spring.mapper.UserCreateEditMapper;
import com.narel.spring.mapper.UserReadMapper;
import com.narel.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserReadMapper userReadMapper;

    @Mock
    private UserCreateEditMapper userCreateEditMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void returnListUsersSuccessful() {
        User user1 = new User();
        user1.setFullName("Jon");
        User user2 = new User();
        user2.setFullName("Jortn");
        List<User> users = List.of(user1, user2);
        UserReadDto userReadDto1 = new UserReadDto(1, "Jon", 34, "3r", "Minsk", Role.USER, "3r", "34d", "35");
        UserReadDto userReadDto2 = new UserReadDto(2, "Jortn", 345, "3r2", "Minsk fr", Role.USER, "3323r", "345d", "3578");
        when(userRepository.findAll()).thenReturn(users);
        when(userReadMapper.map(user1)).thenReturn(userReadDto1);
        when(userReadMapper.map(user2)).thenReturn(userReadDto2);

        List<UserReadDto> result = userService.findAll();

        assertEquals(2, result.size());
        assertEquals(userReadDto1, result.get(0));
        assertEquals(userReadDto2, result.get(1));
        verify(userRepository).findAll();
        verify(userReadMapper).map(user1);
        verify(userReadMapper).map(user2);
    }

    @Test
    void findUserByIdWhenUserExists() {
        Integer userId = 1;
        User user1 = new User();
        UserReadDto userReadDto1 = new UserReadDto(userId, "Jon", 34, "3r", "Minsk", Role.USER, "3r", "34d", "35");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        when(userReadMapper.map(user1)).thenReturn(userReadDto1);

        Optional<UserReadDto> resultById = userService.findById(userId);

        assertTrue(resultById.isPresent());
        assertEquals(userReadDto1, resultById.get());
        verify(userRepository).findById(userId);
        verify(userReadMapper).map(user1);
    }

    @Test
    void findUserByIdWhenUserNotExists() {
        Integer userId = 999999999;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<UserReadDto> resultById = userService.findById(userId);

        assertTrue(resultById.isEmpty());
        verify(userRepository).findById(userId);
        verifyNoInteractions(userReadMapper);
    }

    @Test
    void creatUserSuccessfulAndReturnUser() {
        UserCreateEditDto userCreateEditDto = getUserCreateEditDto();
        User newUser = new User();
        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setFullName("Luke Skywalker");
        UserReadDto expectedDto = new UserReadDto(
                1,
                "Luke Skywolker",
                8954852,
                "moonStation@gmail.com",
                "Tatuin",
                Role.USER,
                "bm2344",
                "fr3535",
                "2353 3553 3253 2353");
        when(userCreateEditMapper.map(userCreateEditDto)).thenReturn(newUser);
        when(userRepository.save(newUser)).thenReturn(savedUser);
        when(userReadMapper.map(savedUser)).thenReturn(expectedDto);

        UserReadDto result = userService.create(userCreateEditDto);

        assertEquals(expectedDto, result);
        verify(userCreateEditMapper).map(userCreateEditDto);
        verify(userRepository).save(newUser);
        verify(userReadMapper).map(savedUser);
    }

    @Test
    void updateUserSuccessfulWhenUserExists() {
        Integer userId = 1;
        UserCreateEditDto editDto = getUserCreateEditDtoTwo();

        User existingUser = new User();
        User editUser = new User();
        editUser.setPhoneNumber(555666);
        editUser.setId(1);
        UserReadDto expectedDto = new UserReadDto(
                userId,
                "Han Solo",
                555666,
                "MillenniumFalcon@gmail.com",
                "Tatuin",
                Role.USER,
                "bm2344",
                "fr3535",
                "2353 3553 3253 2353");
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userCreateEditMapper.map(editDto,
                existingUser)).thenReturn(editUser);
        when(userRepository.saveAndFlush(editUser)).thenReturn(editUser);
        when(userReadMapper.map(editUser)).thenReturn(expectedDto);

        Optional<UserReadDto> result = userService.update(userId, editDto);

        assertTrue(result.isPresent());
        assertEquals(expectedDto, result.get());
        verify(userRepository).findById(userId);
        verify(userCreateEditMapper).map(editDto, existingUser);
        verify(userRepository).saveAndFlush(editUser);
        verify(userReadMapper).map(editUser);
    }

    @Test
    void deleteUserReturnTrueSuccessful() {
        Integer userId = 1;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        boolean result = userService.delete(userId);

        assertTrue(result);
        verify(userRepository).findById(userId);
        verify(userRepository).delete(user);
        verify(userRepository).flush();
    }

    @Test
    void deleteWhenUserNotExistsReturnFalse() {
        Integer userId = 999999999;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        boolean result = userService.delete(userId);

        assertFalse(result);
        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
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
}
