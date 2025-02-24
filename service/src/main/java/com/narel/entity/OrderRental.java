package com.narel.entity;

import com.narel.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_rental")
public class OrderRental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "users")
    private Integer user;
    private Integer car;
    @Column(name = "rental_start_date")
    private LocalDateTime rentalStartDate;
    @Column(name = "rental_end_date")
    private LocalDateTime rentalEndDate;
    @Column(name = "total_rental_cost")
    private Double totalRentalCost;
    @Enumerated(EnumType.STRING)
    private Status status;


}
