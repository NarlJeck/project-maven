package com.narel.spring.mapper;

import com.narel.spring.dto.CarReadDto;
import com.narel.spring.entity.Car;
import com.narel.spring.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarReadMapper implements Mapper<Car, CarReadDto> {

//    private final ReviewReadMapper reviewReadMapper;
//    private final OrderReadMapper orderReadMapper;

    @Override
    public CarReadDto map(Car object) {
        return new CarReadDto(
                object.getId(),
                object.getYear(),
                object.getImage(),
                object.getRentalPrice(),
                object.getRegistrationNumber(),
                object.getBrand(),
                object.getModel(),
                object.getStatus(),
                object.getType(),
                object.getEquipment(),
                object.getFuelType(),
                object.getEngineCapacity());
//                object.getReviews().stream().map(reviewReadMapper::map).toList(),
//                object.getOrderRentals().stream().map(orderReadMapper::map).toList());
    }
}
