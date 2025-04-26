package com.narel.spring.mapper;

import com.narel.spring.dto.CarCreateEditDto;
import com.narel.spring.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarCreateEditMapper implements Mapper<CarCreateEditDto, Car> {

    @Override
    public Car map(CarCreateEditDto fromObject, Car toObject) {
        copy(fromObject, toObject);

        return toObject;
    }

    @Override
    public Car map(CarCreateEditDto object) {
        Car car = new Car();
        copy(object, car);
        return car;
    }

    private static void copy(CarCreateEditDto object, Car car) {

        car.setYear(object.getYear());
        car.setImage(object.getImage());
        car.setRentalPrice(object.getRentalPrice());
        car.setRegistrationNumber(object.getRegistrationNumber());
        car.setBrand(object.getBrand());
        car.setModel(object.getModel());
        car.setStatus(object.getStatus());
        car.setType(object.getType());
        car.setEquipment(object.getEquipment());
        car.setFuelType(object.getFuelType());
        car.setEngineCapacity(object.getEngineCapacity());
    }
}
