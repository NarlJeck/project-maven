package integration;

import annotation.IT;
import com.narel.spring.entity.Car;
import com.narel.spring.entity.Review;
import com.narel.spring.entity.User;
import com.narel.spring.enums.CarStatus;
import com.narel.spring.enums.Role;
import com.narel.spring.enums.Type;
import com.narel.spring.repository.CarRepository;
import config.IntegrationTestBase;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CarTestIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final CarRepository carRepository;

    private static Car getCar1() {
        return Car.builder()
                .year(2020)
                .image("https://assets.avtocod.ru/storage/images/articles-2022/otnyud-ne-bmw-top-10-samykh-dorogikh-mashin-mira-v-2022-godu/otnyud-ne-bmw-top-10-samykh-dorogikh-mashin-mira-v-2022-godu-0-min.jpg")
                .rentalPrice(50.0)
                .registrationNumber("RR55")
                .brand("BMW")
                .model("X7")
                .status(CarStatus.AVAILABLE)
                .Type(Type.LUXURY_CAR)
                .equipment("Подогрев руля, подогрев сидений")
                .fuelType("Diesel")
                .engineCapacity(3.0)
                .build();
    }

    private static Car getCar3() {
        return Car.builder()
                .year(2020)
                .image("https://assets.avtocod.ru/storage/images/articles-2022/otnyud-ne-bmw-top-10-samykh-dorogikh-mashin-mira-v-2022-godu/otnyud-ne-bmw-top-10-samykh-dorogikh-mashin-mira-v-2022-godu-0-min.jpg")
                .rentalPrice(50.0)
                .registrationNumber("RR56")
                .brand("BMW")
                .model("X7")
                .status(CarStatus.AVAILABLE)
                .Type(Type.LUXURY_CAR)
                .equipment("Подогрев руля, подогрев сидений")
                .fuelType("Diesel")
                .engineCapacity(3.0)
                .build();
    }

    private static Car getCar2() {
        return Car.builder()
                .year(2021)
                .image("https://assets.avtocod.ru/storage/images/articles-2022/otnyud-ne-bmw-top-10-samykh-dorogikh-mashin-mira-v-2022-godu/otnyud-ne-bmw-top-10-samykh-dorogikh-mashin-mira-v-2022-godu-0-min.jpg")
                .rentalPrice(60.0)
                .registrationNumber("RR55")
                .brand("AUDI")
                .model("A6")
                .status(CarStatus.AVAILABLE)
                .Type(Type.LUXURY_CAR)
                .equipment("Подогрев руля, подогрев сидений")
                .fuelType("GASOLINE")
                .engineCapacity(2.0)
                .build();
    }

    private static Review getReview1() {
        return Review.builder()
                .user(getUser1())
                .car(getCar1())
                .reviewText("This is nice car")
                .rating(5)
                .createdAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .build();
    }

    private static Review getReview2() {
        return Review.builder()
                .user(getUser1())
                .car(getCar1())
                .reviewText("This is car not fast")
                .rating(5)
                .createdAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .build();
    }

    private static Review getReview3() {
        return Review.builder()
                .user(getUser1())
                .car(getCar3())
                .reviewText("very comfortable car")
                .rating(5)
                .createdAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .build();
    }

    private static User getUser1() {
        return User.builder()
                .fullName("Jon")
                .phoneNumber(299)
                .email("@gmail")
                .residentialAddress("Minsk")
                .role(Role.ADMIN)
                .passport("BM33")
                .driverLicense("PL333")
                .bankCard("343432")
                .password("4gd3O04@gK")
                .build();
    }
}