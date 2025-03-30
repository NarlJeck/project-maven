package integration;

import com.narel.dto.CarFilter;
import com.narel.entity.Car;
import com.narel.entity.Review;
import com.narel.entity.User;
import com.narel.enums.CarStatus;
import com.narel.enums.Role;
import com.narel.enums.Type;
import com.narel.repository.CarRepository;
import com.narel.repository.ReviewRepository;
import com.narel.repository.UserRepository;
import config.ApplicationTestConfiguration;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarTestIT {
    private final static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationTestConfiguration.class);

    private static EntityManager entityManager = context.getBean(EntityManager.class);
    private CarRepository carRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;

    @BeforeAll
    static void setUp() {
    }

    @AfterAll
    static void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
        context.close();
    }

    @BeforeEach
    void beforeStart() {
        entityManager.getTransaction().begin();
        carRepository = context.getBean(CarRepository.class);
        userRepository = context.getBean(UserRepository.class);
        reviewRepository = context.getBean(ReviewRepository.class);
    }

    @AfterEach
    void afterEnding() {
        if (entityManager != null) {
            entityManager.getTransaction().rollback();
            entityManager.close();
        }
    }

    @Test
    void saveCarSuccessfully() {
        Car car1 = getCar1();

        Car saveCar = carRepository.save(car1);

        assertThat(saveCar.getRegistrationNumber().contains("RR55"));
    }

    @Test
    void findCarIdSuccessfully() {
        Car car1 = getCar1();
        carRepository.save(car1);
        entityManager.flush();
        entityManager.clear();

        Optional<Car> foundedCar = carRepository.findById(car1.getId());

        assertEquals(car1.getRegistrationNumber(), foundedCar.get().getRegistrationNumber());
    }

    @Test
    void deleteCarSuccessfully() {
        Car car1 = getCar1();
        carRepository.save(car1);
        entityManager.flush();
        entityManager.clear();

        carRepository.delete(car1);

        entityManager.flush();
        entityManager.clear();
        Optional<Car> deletedCar = carRepository.findById(car1.getId());
        assertEquals(deletedCar.isEmpty(), true);
    }

    @Test
    void updateCarSuccessfully() {
        Car car1 = getCar1();
        carRepository.save(car1);
        entityManager.flush();
        entityManager.clear();
        car1.setYear(2019);

        carRepository.update(car1);

        entityManager.flush();
        entityManager.clear();
        Optional<Car> updatedCar = carRepository.findById(car1.getId());
        assertEquals(2019, updatedCar.get().getYear());
    }

    @Test
    void findAllCarsSuccessfully() {
        Car car1 = getCar1();
        Car car2 = getCar2();
        carRepository.save(car1);
        carRepository.save(car2);
        entityManager.flush();
        entityManager.clear();

        List<Car> actualListAllCar = carRepository.findAll();

        assertThat(actualListAllCar)
                .hasSize(2)
                .contains(car1, car2);
    }

    @Test
    void findBrandOrYearOrFuelType() {
        Car car1 = getCar1();
        Car car2 = getCar2();
        Car car3 = getCar3();
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        entityManager.flush();
        entityManager.clear();
        CarFilter carFilter = CarFilter.builder()
                .brand("BMW")
                .year(2020)
                .build();
        var actualCarModel = carRepository.findCarModelByFilter(carFilter);

        assertThat(actualCarModel)
                .hasSize(2);
    }

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