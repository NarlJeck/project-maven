package com.narel.spring.repository;

import com.narel.spring.entity.Car;
import com.narel.spring.filter.CarFilter;

import java.util.List;


public interface FilterCarRepository {

    List<Car> findAllByFilter(CarFilter filter);
}
