package integration;

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

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReviewTestIT {
    static SessionFactory sessionFactory;
    Session session = null;

    @BeforeAll
    public static void setUp() {

        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @BeforeEach
    public void beforeStartTest() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    public void afterEndingTest() {
        if (session != null) {
            session.getTransaction().rollback();
            session.close();
        }
    }

    @Test
    void checkCreateReviewSuccessfully() {
        Review review1 = getReviewInSession();

        assertNotNull(review1.getId());
    }

    @Test
    void checkReadReviewSuccessfully() {
        Review review1 = getReviewInSession();

        Review foundReview = session.get(Review.class, review1.getId());

        assertNotNull(review1.getId());
        assertEquals(review1.getReviewText(), foundReview.getReviewText());
        assertEquals(review1.getId(), foundReview.getId());
    }

    @Test
    void checkUpdateReviewSuccessfully() {
        Review review1 = getReviewInSession();

        review1.setRating(1);
        session.update(review1);
        session.flush();
        Review updatedReview = session.get(Review.class, review1.getId());

        assertNotNull(updatedReview);
        assertEquals(1, updatedReview.getRating());
    }

    @Test
    void checkDeleteReviewSuccessfully() {
        Review review1 = getReviewInSession();

        session.delete(review1);
        session.flush();
        Review deletedReview = session.get(Review.class, review1.getId());

        assertNull(deletedReview);
    }

    private Review getReviewInSession() {
        User user1 = getUser1();
        Car car1 = getCar1();
        Review review1 = getReviewInApplication();
        user1.addReview(review1);
        car1.addReviews(review1);

        session.persist(user1);
        session.persist(car1);
        session.flush();
        session.persist(review1);
        session.flush();
        return review1;
    }

    private static Review getReviewInApplication() {
        return Review.builder()
                .user(getUser1())
                .car(getCar1())
                .reviewText("This is nice car")
                .rating(5)
                .createdAt(LocalDateTime.now())
                .build();
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
