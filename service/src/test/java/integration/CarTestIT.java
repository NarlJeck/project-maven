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
import config.ApplicationTestConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
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

@Import(ApplicationTestConfiguration.class)
public class CarTestIT {

    private static SessionFactory sessionFactory;
    CarRepository carRepository;
    UserRepository userRepository;
    ReviewRepository reviewRepository;

    @BeforeAll
    static void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationTestConfiguration.class);
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
        carRepository = new CarRepository();
        userRepository = new UserRepository();
        reviewRepository = new ReviewRepository();
    }

    @AfterEach
    void afterEnding() {
        if (sessionFactory.getCurrentSession() != null) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            sessionFactory.getCurrentSession().close();
        }
    }

    @Test
    void saveCarSuccessfully() {
        Car car1 = getCar1();

        Car saveCar = carRepository.save(car1);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
        assertNotNull(saveCar);
    }
//
    @Test
    void findCarIdSuccessfully() {
        Car car1 = getCar1();
        carRepository.save(car1);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        Optional<Car> foundedCar = carRepository.findById(car1.getId());

        assertNotNull(foundedCar);
        assertEquals(car1.getRegistrationNumber(), foundedCar.get().getRegistrationNumber());
    }

    @Test
    void deleteCarSuccessfully() {
        Car car1 = getCar1();
        carRepository.save(car1);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        carRepository.delete(car1);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
        Optional<Car> deletedCar = carRepository.findById(car1.getId());
        assertEquals(deletedCar.isEmpty(), true);
    }

    @Test
    void updateCarSuccessfully() {
        Car car1 = getCar1();
        carRepository.save(car1);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
        car1.setYear(2019);

        carRepository.update(car1);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
        Optional<Car> updatedCar = carRepository.findById(car1.getId());
        assertEquals(2019, updatedCar.get().getYear());

    }

    @Test
    void findAllCarsSuccessfully() {
        Car car1 = getCar1();
        Car car2 = getCar2();
        carRepository.save(car1);
        carRepository.save(car2);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

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
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
        CarFilter carFilter = CarFilter.builder()
                .brand("BMW")
                .year(2020)
                .build();
        var expectedCarModel1 = sessionFactory.getCurrentSession().get(Car.class, car1.getId()).getModel();
        var expectedCarModel3 = sessionFactory.getCurrentSession().get(Car.class, car3.getId()).getModel();
        List<String> expectedCarModel = Arrays.asList(expectedCarModel1, expectedCarModel3);

        var actualCarModel = carRepository.findCarModelByFilter(sessionFactory.getCurrentSession(), carFilter);

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
        sessionFactory.getCurrentSession().flush();
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
        CarReviewsFilter carReviewsFilter = CarReviewsFilter.builder()
                .brand("BMW")
                .model("X7")
                .build();

        var actualCarModel = carRepository.findReviewsByFilter(sessionFactory.getCurrentSession(), carReviewsFilter);

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