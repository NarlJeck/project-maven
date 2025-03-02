package com.narel.entity;

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
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer year;
    @Column(name = "number_seats")
    private Integer numberSeats;
    @Column(name = "rental_price_per_day")
    private Double rentalPrice;
    @Column(name = "registration_number")
    private String registrationNumber;
    private String brand;
    private String model;
    private Boolean status;
    @Column(name = "car_type")
    private Integer carType;
    private String equipment;
    @Column(name = "fuel_type")
    private String fuelType;
    @Column(name = "engine_capacity")
    private Integer engineCapacity;

}
