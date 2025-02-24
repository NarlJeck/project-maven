package com.narel.entity;

import com.narel.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private Integer phoneNumber;
    private String email;
    @Column(name = "residential_address")
    private String residentialAddress;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String passport;
    @Column(name = "driver_license")
    private String driverLicense;
    @Column(name = "bank_card")
    private String bankCard;
    private String password;

}
