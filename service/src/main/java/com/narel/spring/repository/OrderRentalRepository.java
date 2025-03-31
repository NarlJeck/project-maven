package com.narel.spring.repository;

import com.narel.spring.entity.OrderRental;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRentalRepository extends BaseRepository<Integer, OrderRental> {

    public OrderRentalRepository() {
        super(OrderRental.class);
    }
}
