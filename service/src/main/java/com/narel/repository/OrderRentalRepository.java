package com.narel.repository;

import com.narel.entity.OrderRental;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRentalRepository extends BaseRepository<Integer, OrderRental> {

    public OrderRentalRepository() {
        super(OrderRental.class);
    }
}
