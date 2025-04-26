package com.narel.spring.service;

import com.narel.spring.dto.CarCreateEditDto;
import com.narel.spring.dto.CarReadDto;
import com.narel.spring.filter.CarFilter;
import com.narel.spring.filter.QPredicate;
import com.narel.spring.mapper.CarCreateEditMapper;
import com.narel.spring.mapper.CarReadMapper;
import com.narel.spring.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.narel.spring.entity.QCar.car;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;
    private final CarReadMapper carReadMapper;
    private final CarCreateEditMapper carCreateEditMapper;

    public List<CarReadDto> findAll() {
        return carRepository.findAll().stream()
                .map(carReadMapper::map)
                .toList();
    }

    public Page<CarReadDto> findAll(CarFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getBrand(), car.brand::containsIgnoreCase)
                .add(filter.getFuelType(), car.fuelType::containsIgnoreCase)
                .add(filter.getYear(), car.year::eq)
                .add(filter.getType(), car.Type::eq)
                .add(filter.getRentalPrice(), car.rentalPrice::loe)
                .add(filter.getEngineCapacity(), car.engineCapacity::eq)
                .buildAnd();
        return carRepository.findAll(predicate,pageable)
                .map(carReadMapper::map);

    }

    Optional<CarReadDto> findById(Integer id) {
        return carRepository.findById(id)
                .map(carReadMapper::map);
    }

    @Transactional
    public CarReadDto create(CarCreateEditDto carDto) {
        return Optional.of(carDto)
                .map(carCreateEditMapper::map)
                .map(carRepository::save)
                .map(carReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CarReadDto> update(Integer id, CarCreateEditDto carDto) {
        return carRepository.findById(id)
                .map(entity -> carCreateEditMapper.map(carDto, entity))
                .map(carRepository::saveAndFlush)
                .map(carReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return carRepository.findById(id)
                .map(entity -> {
                    carRepository.delete(entity);
                    carRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
