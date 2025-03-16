package com.narel.repository;

import com.narel.entity.OrderRental;
import jakarta.persistence.EntityManager;

public class OrderRentalRepository extends BaseRepository<Integer,OrderRental>{

    public OrderRentalRepository(EntityManager entityManager) {
        super(OrderRental.class, entityManager);
    }
}
