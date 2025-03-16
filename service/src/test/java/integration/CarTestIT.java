package integration;

import com.narel.dao.CarDao;
import com.narel.dto.CarFilter;
import com.narel.entity.Car;
import com.narel.entity.Review;
import com.narel.entity.User;
import com.narel.enums.CarStatus;
import com.narel.enums.Role;
import com.narel.enums.Type;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CarTestIT {

    private final static CarDao carDao = CarDao.getInstance();

    private static SessionFactory sessionFactory;
    private Session session = null;

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
        session = sessionFactory.openSession();
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
    void findBrandOrYearOrFuelType() {
        Car car1 = getCar1();
        Car car2 = getCar2();
        Car car3 = getCar3();
        session.persist(car1);
        session.persist(car2);
        session.persist(car3);
        session.flush();
        session.clear();
        CarFilter carFilter = CarFilter.builder()
                .brand("BMW")
                .year(2020)
                .build();
        var expectedCarModel1 = session.get(Car.class, car1.getId()).getModel();
        var expectedCarModel3 = session.get(Car.class, car3.getId()).getModel();
        List<String> expectedCarModel = Arrays.asList(expectedCarModel1, expectedCarModel3);

        var actualCarModel = carDao.findCarModelByFilter(session, carFilter);

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
        session.persist(user1);
        session.flush();
        session.persist(car1);
        session.persist(car2);
        session.persist(car3);
        session.persist(review1);
        session.persist(review2);
        session.persist(review3);
        session.flush();
        session.clear();

        var actualCarModel = carDao.findReviewsByBrandAndModelCar(session, "BMW", "X7");

        assertThat(actualCarModel)
                .hasSize(3)
                .containsAnyOf(review1.getReviewText(), review2.getReviewText());
    }

    @Test
    void checkCreateCarSuccessfully() {
        Car car1 = getCar1();

        session.persist(car1);
        session.flush();
        session.clear();

        assertNotNull(car1.getId());
    }

    @Test
    void checkReadCarSuccessfully() {
        Car car1 = getCar1();
        session.persist(car1);
        session.flush();
        session.clear();

        Car founderCar = session.get(Car.class, car1.getId());

        assertNotNull(founderCar);
        assertEquals(car1.getRegistrationNumber(), founderCar.getRegistrationNumber());
        assertEquals(car1.getImage(), founderCar.getImage());
    }

    @Test
    void checkUpdateCarSuccessfully() {
        Car car1 = getCar1();
        session.persist(car1);
        session.flush();
        session.clear();
        car1.setRentalPrice(70.0);

        session.merge(car1);

        session.flush();
        session.clear();
        Car updatedCar = session.get(Car.class, car1.getId());
        assertNotNull(updatedCar);
        assertEquals(70.0, updatedCar.getRentalPrice());
    }

    @Test
    void checkDeleteCarSuccessfully() {
        Car car1 = getCar1();
        session.persist(car1);
        session.flush();
        session.clear();

        session.remove(car1);
        session.flush();
        session.clear();
        Car deletedCar = session.get(Car.class, car1.getId());

        assertNull(deletedCar);
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