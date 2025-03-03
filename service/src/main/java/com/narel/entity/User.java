package com.narel.entity;

import com.narel.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(exclude = {"reviews", "orderRentals", "password"})
@ToString(exclude = {"reviews", "orderRentals", "password"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;

    private Integer phoneNumber;

    private String email;

    private String residentialAddress;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String passport;

    private String driverLicense;

    private String bankCard;

    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<OrderRental> orderRentals = new ArrayList<>();

    public void addReview(Review review) {
        reviews.add(review);
        review.setUser(this);
    }

    public void addOrderRental(OrderRental orderRental) {
        orderRentals.add(orderRental);
        orderRental.setUser(this);
    }

}
