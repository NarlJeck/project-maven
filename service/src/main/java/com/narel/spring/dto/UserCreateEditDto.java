package com.narel.spring.dto;

import com.narel.spring.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    String fullName;

//    @Pattern(regexp = "^(25|29|33|44)([1-9]\\d{6})$",
//            message = "Incorrect number format (correct 291112233)")
    Integer phoneNumber;

//    @Email
    String email;

    String residentialAddress;
    Role role = Role.USER;

//    @NotEmpty
    String passport;

//    @NotEmpty
    String driverLicense;

//    @Size(min = 16, max = 16)
    String bankCard;

//    @NotEmpty
    String password;
}
