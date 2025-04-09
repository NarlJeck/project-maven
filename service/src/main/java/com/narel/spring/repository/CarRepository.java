package com.narel.spring.repository;

import com.narel.spring.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface CarRepository extends CrudRepository<Car, Integer> {
}
