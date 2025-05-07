package com.narel.spring.service;

import com.narel.spring.dto.ReviewCreateDto;
import com.narel.spring.dto.ReviewReadDto;
import com.narel.spring.filter.QPredicate;
import com.narel.spring.filter.ReviewsFilter;
import com.narel.spring.mapper.ReviewCreateMapper;
import com.narel.spring.mapper.ReviewReadMapper;
import com.narel.spring.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.narel.spring.entity.QReview.review;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewReadMapper reviewReadMapper;
    private final ReviewCreateMapper reviewCreateMapper;

    public Page<ReviewReadDto> findAll(ReviewsFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getBrand(), review.car.brand::containsIgnoreCase)
                .add(filter.getModel(), review.car.model::containsIgnoreCase)
                .buildAnd();
        return reviewRepository.findAll(predicate, pageable)
                .map(reviewReadMapper::map);
    }

    public Optional<ReviewReadDto> findById(Integer id) {
        return reviewRepository.findById(id)
                .map(reviewReadMapper::map);
    }

    @Transactional
    public ReviewReadDto create(ReviewCreateDto reviewCreateDto) {
        return Optional.of(reviewCreateDto)
                .map(reviewCreateMapper::map)
                .map(reviewRepository::save)
                .map(reviewReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Integer id) {
        return reviewRepository.findById(id)
                .map(entity -> {
                    reviewRepository.delete(entity);
                    reviewRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
