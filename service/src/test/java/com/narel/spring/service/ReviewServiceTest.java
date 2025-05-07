package com.narel.spring.service;

import com.narel.spring.dto.CarReadDto;
import com.narel.spring.dto.ReviewCreateDto;
import com.narel.spring.dto.ReviewReadDto;
import com.narel.spring.dto.UserReadDto;
import com.narel.spring.entity.Car;
import com.narel.spring.entity.Review;
import com.narel.spring.entity.User;
import com.narel.spring.enums.CarStatus;
import com.narel.spring.enums.Role;
import com.narel.spring.enums.Type;
import com.narel.spring.mapper.ReviewCreateMapper;
import com.narel.spring.mapper.ReviewReadMapper;
import com.narel.spring.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestConstructor;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewReadMapper reviewReadMapper;

    @Mock
    private ReviewCreateMapper reviewCreateMapper;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void createReviewSuccessful() {
        User user = new User();
        user.setId(1);
        UserReadDto userReadDto = new UserReadDto(
                1,
                "Luke Skywolker",
                8954852,
                "moonStation@gmail.com",
                "Tatuin",
                Role.USER,
                "bm2344",
                "fr3535",
                "2353 3553 3253 2353");
        CarReadDto carReadDto = new CarReadDto(
                1,
                2002,
                "22",
                new BigDecimal("20.00"),
                "324",
                "BMW",
                "e92",
                CarStatus.AVAILABLE,
                Type.LUXURY_CAR,
                "Cool",
                "diz",
                3.0
        );
        Car car = new Car();
        car.setId(1);
        ReviewCreateDto createReviewDto = ReviewCreateDto.builder()
                .createAd(Instant.now())
                .carId(1)
                .userId(1)
                .reviewText("This car is greatest")
                .rating(5)
                .build();
        Review newReview = new Review();
        Review savedReview = new Review();
        savedReview.setId(1);
        savedReview.setReviewText("This car is greatest");
        savedReview.setCar(car);
        savedReview.setUser(user);
        savedReview.setCreatedAt(Instant.now());
        savedReview.setRating(5);
        ReviewReadDto expectedDto = new ReviewReadDto(
                1, userReadDto, carReadDto, "This car is greatest", 5, Instant.now());
        when(reviewCreateMapper.map(createReviewDto)).thenReturn(newReview);
        when(reviewRepository.save(newReview)).thenReturn(savedReview);
        when(reviewReadMapper.map(savedReview)).thenReturn(expectedDto);

        ReviewReadDto result = reviewService.create(createReviewDto);

        assertEquals(expectedDto, result);
        verify(reviewCreateMapper).map(createReviewDto);
        verify(reviewRepository).save(newReview);
        verify(reviewReadMapper).map(savedReview);
    }
}
