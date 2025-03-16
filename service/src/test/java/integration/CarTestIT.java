package integration;

import com.narel.dto.CarFilter;
import com.narel.dto.CarReviewsFilter;
import com.narel.entity.Car;
import com.narel.entity.Review;
import com.narel.entity.User;
import com.narel.enums.CarStatus;
import com.narel.enums.Role;
import com.narel.enums.Type;
import com.narel.repository.CarRepository;
import com.narel.repository.ReviewRepository;
import com.narel.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;

import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarTestIT {

    private static SessionFactory sessionFactory;
    private Session session = sessionFactory.getCurrentSession();
    private CarRepository carRepository = new CarRepository(session);
    private UserRepository userRepository = new UserRepository(session);
    private ReviewRepository reviewRepository = new ReviewRepository(session);

    @BeforeAll
    static void setUp() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @AfterAll
    static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @BeforeEach
    void beforeStart() {
        var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        session.beginTransaction();
    }

    @AfterEach
    void afterEnding() {
        if (session != null) {
            session.getTransaction().rollback();
            session.close();
        }
    }

    @Test
    void saveCarSuccessfully() {
        Car car1 = getCar1();

        Car saveCar= carRepository.save(car1);

        session.flush();
        session.clear();

        assertNotNull(saveCar);
    }

    @Test
    void findCarIdSuccessfully() {
        Car car1 = getCar1();
        carRepository.save(car1);
        session.flush();
        session.clear();

        Optional<Car> foundedCar = carRepository.findById(car1.getId());

        assertNotNull(foundedCar);
        assertEquals(car1.getRegistrationNumber(), foundedCar.get().getRegistrationNumber());
    }

    @Test
    void deleteCarSuccessfully() {
        Car car1 = getCar1();
        carRepository.save(car1);
        session.flush();
        session.clear();

        carRepository.delete(car1);

        session.flush();
        session.clear();
        Optional<Car> deletedCar = carRepository.findById(car1.getId());
        assertEquals(deletedCar.isEmpty(), true);
    }

    @Test
    void updateCarSuccessfully() {
        Car car1 = getCar1();
        carRepository.save(car1);
        session.flush();
        session.clear();
        car1.setYear(2019);

        carRepository.update(car1);

        session.flush();
        session.clear();
        Optional<Car> updatedCar = carRepository.findById(car1.getId());
        assertEquals(2019, updatedCar.get().getYear());

    }

    @Test
    void findAllCarsSuccessfully() {
        Car car1 = getCar1();
        Car car2 = getCar2();
        carRepository.save(car1);
        carRepository.save(car2);
        session.flush();
        session.clear();

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
        session.flush();
        session.clear();
        CarFilter carFilter = CarFilter.builder()
                .brand("BMW")
                .year(2020)
                .build();
        var expectedCarModel1 = session.get(Car.class, car1.getId()).getModel();
        var expectedCarModel3 = session.get(Car.class, car3.getId()).getModel();
        List<String> expectedCarModel = Arrays.asList(expectedCarModel1, expectedCarModel3);

        var actualCarModel = carRepository.findCarModelByFilter(session, carFilter);

        assertThat(actualCarModel)
                .hasSize(2)
                .containsAnyOf(expectedCarModel.get(0), expectedCarModel.get(1));
    }

    @Test
    void findReviewsByCarBrandAndModel() {
        Car car1 = getCar1();
        Car car2 = getCar2();
        Car car3 = getCar3();
        Review review1 = getReview1();
        Review review2 = getReview2();
        Review review3 = getReview3();
        User user1 = getUser1();
        user1.addReview(review1);
        user1.addReview(review2);
        user1.addReview(review3);
        car1.addReviews(review1);
        car1.addReviews(review2);
        car3.addReviews(review3);
        userRepository.save(user1);
        session.flush();
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        session.flush();
        session.clear();
        CarReviewsFilter carReviewsFilter = CarReviewsFilter.builder()
                .brand("BMW")
                .model("X7")
                .build();

        var actualCarModel = carRepository.findReviewsByBrandAndModelCar(session, carReviewsFilter);

        assertThat(actualCarModel)
                .hasSize(3)
                .containsAnyOf(review1.getReviewText(), review2.getReviewText());
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