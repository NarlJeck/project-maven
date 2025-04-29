package com.narel.spring.mapper;

import com.narel.spring.dto.CarReadDto;
import com.narel.spring.dto.ReviewReadDto;
import com.narel.spring.dto.UserReadDto;
import com.narel.spring.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewReadMapper implements Mapper<Review, ReviewReadDto> {

    private final UserReadMapper userReadMapper;
    private final CarReadMapper carReadMapper;

    @Override
    public ReviewReadDto map(Review object) {
        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        CarReadDto car = Optional.ofNullable(object.getCar())
                .map(carReadMapper::map)
                .orElse(null);

        return new ReviewReadDto(
                object.getId(),
                user,
                car,
                object.getReviewText(),
                object.getRating(),
                object.getCreatedAt());
    }
}
