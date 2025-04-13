package com.narel.spring.integration;

import com.narel.spring.config.IntegrationTestBase;
import com.narel.spring.dto.UserCreateEditDto;
import com.narel.spring.dto.UserReadDto;
import com.narel.spring.enums.Role;
import com.narel.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static com.narel.spring.dto.UserCreateEditDto.Fields.bankCard;
import static com.narel.spring.dto.UserCreateEditDto.Fields.driverLicense;
import static com.narel.spring.dto.UserCreateEditDto.Fields.email;
import static com.narel.spring.dto.UserCreateEditDto.Fields.fullName;
import static com.narel.spring.dto.UserCreateEditDto.Fields.passport;
import static com.narel.spring.dto.UserCreateEditDto.Fields.password;
import static com.narel.spring.dto.UserCreateEditDto.Fields.phoneNumber;
import static com.narel.spring.dto.UserCreateEditDto.Fields.residentialAddress;
import static com.narel.spring.dto.UserCreateEditDto.Fields.role;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final UserService userService;

    @Test
    void createSuccessfulAndSuccessfulRedirect() throws Exception {
        mockMvc.perform(post("/users")
                        .param(fullName, "Marty McFlay")
                        .param(phoneNumber, "8935352")
                        .param(email, "martyFly@gmail.com")
                        .param(residentialAddress, "USA st. Kolinsa 33")
                        .param(role, "USER")
                        .param(passport, "RR3532")
                        .param(driverLicense, "ev3535311")
                        .param(bankCard, "3423 2353 2111 3535")
                        .param(password, "111")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

    @Test
    void findAllSuccessful() throws Exception {
        mockMvc.perform(get("/users"))

                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void findByIdSuccessful() throws Exception {
        UserReadDto user = getUserReadDto();

        mockMvc.perform(get("/users/" + user.getId()))

                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/user"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void updateSuccessfulAndSuccessfulRedirect() throws Exception {
        UserReadDto userReadDto = getUserReadDto();

        mockMvc.perform(post("/users/" + userReadDto.getId() + "/update")
                        .param(fullName, "Jon Brezenk")
                        .param(phoneNumber, "294443999")
                        .param(email, "DarkSide@gmail.com")
                        .param(residentialAddress, "Minsk st. Kolinina 33")
                        .param(role, "ADMIN")
                        .param(passport, "BM4242344")
                        .param(driverLicense, "ev353535")
                        .param(bankCard, "3423 2353 2352 3535")
                        .param(password, "123")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

    @Test
    void deleteSuccessfulAndSuccessfulRedirect() throws Exception {
        UserReadDto user = getUserReadDto();

        mockMvc.perform(post("/users/" + user.getId() + "/delete"))

                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users"),
                        model().attributeDoesNotExist("user"));

    }

    private UserReadDto getUserReadDto() {
        return userService.create(new UserCreateEditDto("Jeck", 244, "dg@.com", "efe", Role.ADMIN, "BM3435550", "3535", "35335", "e455"));
    }
}
