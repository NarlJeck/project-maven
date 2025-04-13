package com.narel.spring.repository;

import com.narel.spring.entity.OrderRental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRentalRepository extends JpaRepository<OrderRental, Integer> {
}
