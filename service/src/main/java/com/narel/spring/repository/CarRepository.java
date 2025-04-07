package com.narel.spring.repository;

import com.narel.spring.entity.Car;
import org.springframework.data.repository.Repository;

public interface CarRepository extends Repository<Car, Integer> {

    Car save(Car entity);

    void delete(Car entity);
}
