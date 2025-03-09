package com.narel.entity;

import com.narel.enums.CarStatus;
import com.narel.enums.Type;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(name = "ReviewsWithCar",
        attributeNodes = {
        @NamedAttributeNode("reviews")
        })
@Data
@EqualsAndHashCode(exclude = {"reviews", "orderRentals", "id"})
@ToString(exclude = {"reviews", "orderRentals", "image", "id"})
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
