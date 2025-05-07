package com.narel.spring.dto;

import com.narel.spring.enums.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserReadDto {

    Integer id;
    String fullName;
    Integer phoneNumber;
    String email;
    String residentialAddress;
    Role role;
    String passport;
    String driverLicense;
    String bankCard;
}
