package com.narel.spring.repository;

import com.narel.spring.entity.Car;
import com.narel.spring.filter.CarFilter;
import com.narel.spring.filter.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.narel.spring.entity.QCar.car;

@RequiredArgsConstructor
public class FilterCarRepositoryImpl implements FilterCarRepository {

    private final EntityManager entityManager;

    @Override
    public List<Car> findAllByFilter(CarFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getBrand(), car.brand::containsIgnoreCase)
                .add(filter.getFuelType(), car.fuelType::containsIgnoreCase)
                .add(filter.getYear(),car.year::eq)
                .add(filter.getType(),car.Type::eq)
                .add(filter.getRentalPrice(), car.rentalPrice::loe)
                .add(filter.getEngineCapacity(), car.engineCapacity::eq)
                .buildAnd();

        return new JPAQuery<Car>(entityManager)
                .select(car)
                .from(car)
                .where(predicate)
                .fetch();
    }
}
