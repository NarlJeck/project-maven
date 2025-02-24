package com.narel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "users")
    private Integer user;
    @Column(name = "car")
    private Integer car;
    @Column(name = "review_text")
    private String reviewText;
    private Integer rating;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
