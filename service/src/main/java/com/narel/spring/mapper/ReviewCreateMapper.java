package com.narel.spring.mapper;

import com.narel.spring.dto.ReviewCreateDto;
import com.narel.spring.entity.Car;
import com.narel.spring.entity.Review;
import com.narel.spring.entity.User;
import com.narel.spring.repository.CarRepository;
import com.narel.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewCreateMapper implements Mapper<ReviewCreateDto, Review>{

    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    public Review map(ReviewCreateDto fromObject, Review toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Review map(ReviewCreateDto object) {
        Review review = new Review();
        copy(object, review);
        return review;
    }

    private void copy(ReviewCreateDto object, Review review) {
        review.setCreatedAt(object.getCreateAd());
        review.setReviewText(object.getReviewText());
        review.setUser(getUser(object.getUserId()));
        review.setCar(getCar(object.getCarId()));
        review.setRating(object.getRating());
    }

    public User getUser(Integer userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }

    public Car getCar(Integer carId) {
        return Optional.ofNullable(carId)
                .flatMap(carRepository::findById)
                .orElse(null);
    }
}
