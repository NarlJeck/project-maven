--liquibase formatted sql

--changeset narel:1
CREATE TABLE users
(
    id                  SERIAL PRIMARY KEY,
    full_name           VARCHAR(128) NOT NULL UNIQUE,
    phone_number        INT          NOT NULL UNIQUE,
    email               VARCHAR(128) UNIQUE,
    residential_address VARCHAR(128),
    role                VARCHAR(32),
    passport            VARCHAR(128) NOT NULL UNIQUE,
    driver_license      VARCHAR(128) NOT NULL UNIQUE,
    bank_card           VARCHAR(128),
    password            VARCHAR(32)  NOT NULL
);
--rollback DROP TABLE users;

--changeset narel:2
CREATE TABLE car
(
    id                   SERIAL PRIMARY KEY,
    year                 INT     NOT NULL,
    image                VARCHAR(256),
    rental_price_per_day DECIMAL NOT NULL,
    registration_number  VARCHAR NOT NULL UNIQUE,
    brand                VARCHAR(50),
    model                VARCHAR(50),
    status               VARCHAR(50),
    type                 VARCHAR(50),
    equipment            VARCHAR(128),
    fuel_type            VARCHAR(128),
    engine_capacity      DECIMAL
);
--rollback DROP TABLE car;

--changeset narel:3
CREATE TABLE review
(
    id          SERIAL PRIMARY KEY,
    user_id     INT REFERENCES users (id) NOT NULL,
    car_id      INT REFERENCES car (id)   NOT NULL,
    review_text VARCHAR(128),
    rating      INT CHECK (rating >= 1 AND rating <= 5),
    created_at  TIMESTAMP DEFAULT current_timestamp
);
--rollback DROP TABLE review;

--changeset narel:4
CREATE TABLE order_rental
(
    id                SERIAL PRIMARY KEY,
    user_id           INT REFERENCES users (id) NOT NULL,
    car_id            INT REFERENCES car (id)   NOT NULL,
    rental_start_time TIMESTAMP                 NOT NULL,
    rental_end_time   TIMESTAMP                 NOT NULL,
    total_rental_cost DECIMAL,
    status            VARCHAR(50)
);
--rollback DROP TABLE order_rental;


