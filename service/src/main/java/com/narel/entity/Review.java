package com.narel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    private String reviewText;

    private Integer rating;

    private Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(user.getId(), review.user.getId())
                && Objects.equals(car.getId(), review.car.getId())
                && Objects.equals(reviewText, review.reviewText)
                && Objects.equals(rating, review.rating)
                && Objects.equals(createdAt, review.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getId(), car.getId(), reviewText, rating, createdAt);
    }

    @Override
    public String toString() {
        return "Review{" +
                "user.id=" + user.getId() +
                ", car.id=" + car.getId() +
                ", reviewText='" + reviewText + '\'' +
                ", rating=" + rating +
                ", createdAt=" + createdAt +
                '}';
    }
}
