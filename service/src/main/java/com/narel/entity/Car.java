package com.narel.entity;

import com.narel.enums.CarStatus;
import com.narel.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"reviews", "orderRentals"})
@ToString(exclude = {"reviews", "orderRentals", "image"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer year;

    private String image;

    @Column(name = "rental_price_per_day")
    private Double rentalPrice;

    private String registrationNumber;

    private String brand;

    private String model;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    @Enumerated(EnumType.STRING)
    private Type Type;

    private String equipment;

    private String fuelType;

    private Double engineCapacity;

    @Builder.Default
    @OneToMany(mappedBy = "car")
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "car")
    private List<OrderRental> orderRentals = new ArrayList<>();

    public void addReviews(Review review) {
        reviews.add(review);
        review.setCar(this);
    }

    public void addOrderRental(OrderRental orderRental) {
        orderRentals.add(orderRental);
        orderRental.setCar(this);
    }

}
