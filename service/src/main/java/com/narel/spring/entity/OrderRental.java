package com.narel.spring.entity;

import com.narel.spring.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    private Instant rentalStartTime;

    private Instant rentalEndTime;

    private BigDecimal totalRentalCost;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderRental that = (OrderRental) o;
        return Objects.equals(user.getId(), that.user.getId()) && Objects.equals(car.getId(), that.car.getId())
                && Objects.equals(rentalStartTime, that.rentalStartTime)
                && Objects.equals(rentalEndTime, that.rentalEndTime)
                && Objects.equals(totalRentalCost, that.totalRentalCost)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getId(), car.getId(), rentalStartTime, rentalEndTime, totalRentalCost, status);
    }

    @Override
    public String toString() {
        return "OrderRental{" +
                "user.id=" + user.getId() +
                ", car.id=" + car.getId() +
                ", rentalStartTime=" + rentalStartTime +
                ", rentalEndTime=" + rentalEndTime +
                ", totalRentalCost=" + totalRentalCost +
                ", status=" + status +
                '}';
    }
}
