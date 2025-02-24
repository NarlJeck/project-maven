CREATE TABLE users
(
    id                  SERIAL PRIMARY KEY,
    full_name           VARCHAR(128) NOT NULL,
    phone_number        INT          NOT NULL,
    email               VARCHAR(128) NULL,
    residential_address VARCHAR(128) NULL,
    role                VARCHAR(32)  NULL,
    passport            VARCHAR(128) NOT NULL UNIQUE,
    driver_license      VARCHAR(128) NOT NULL UNIQUE,
    bank_card           VARCHAR(128) NULL,
    password            VARCHAR(32)  NOT NULL

);
CREATE TABLE car_type
(
    id        SERIAL PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL
);

CREATE TABLE car
(
    id                   SERIAL PRIMARY KEY,
    year                 INT          NOT NULL,
    number_seats         INT          NULL,
    rental_price_per_day DECIMAL      NOT NULL,
    registration_number  VARCHAR      NOT NULL UNIQUE,
    brand                VARCHAR(50)  NULL,
    model                VARCHAR(50)  NULL,
    status               BOOLEAN DEFAULT TRUE,
    car_type             INT REFERENCES car_type (id),
    equipment            VARCHAR(128) NULL,
    fuel_type            VARCHAR(128) NULL,
    engine_capacity      INT          NULL
);
CREATE TABLE review
(
    id          SERIAL PRIMARY KEY,
    users       INT REFERENCES users (id),
    car         INT REFERENCES car (id),
    review_text VARCHAR(128) NULL,
    rating      INT CHECK (rating >= 1 AND rating <= 5),
    created_at  TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE order_rental
(
    id                SERIAL PRIMARY KEY,
    users             INT REFERENCES users (id),
    car               INT REFERENCES car (id),
    rental_start_date TIMESTAMP    NOT NULL,
    rental_end_date   TIMESTAMP    NOT NULL,
    total_rental_cost DECIMAL      NULL,
    status            VARCHAR(128) NULL
);


DROP TABLE review;
DROP TABLE order_rental;
DROP TABLE users;
DROP TABLE car;
DROP TABLE car_type;


