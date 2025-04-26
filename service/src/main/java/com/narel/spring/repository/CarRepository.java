package com.narel.spring.repository;

import com.narel.spring.entity.Car;
import com.narel.spring.entity.QCar;
import com.narel.spring.filter.CarFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface CarRepository extends JpaRepository<Car, Integer>,FilterCarRepository, QuerydslPredicateExecutor<Car>{
}
