package com.narel.repository;

import com.narel.dto.CarFilter;
import com.narel.dto.CarReviewsFilter;
import com.narel.dto.CriteriaPredicate;
import com.narel.dto.QPredicate;
import com.narel.entity.Car;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.narel.entity.QCar.car;

@Repository
public class CarRepository extends BaseRepository<Integer, Car> {

    public CarRepository() {
        super(Car.class);
    }

    public List<String> findCarModelByFilter(CarFilter filter) {
        QPredicate builder = QPredicate.builder();
        builder
                .add(filter.getBrand(), car.brand::eq)
                .add(filter.getYear(), car.year::eq)
                .add(filter.getFuelType(), car.fuelType::eq)
                .add(filter.getEngineCapacity(), car.engineCapacity::eq);
        var predicate = builder
                .buildAnd();

        return new JPAQuery<>()
                .select(car.model)
                .from(car)
                .where(predicate)
                .fetch();
    }
}
