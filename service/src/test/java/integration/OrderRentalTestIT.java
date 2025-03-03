package integration;

import com.narel.entity.Car;
import com.narel.entity.OrderRental;
import com.narel.entity.Review;
import com.narel.entity.User;
import com.narel.enums.CarStatus;
import com.narel.enums.OrderStatus;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderRentalTestIT {
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
    void checkCreateOrderSuccessfully() {
        OrderRental orderRental = getOrderInSession();

        assertNotNull(orderRental.getId());
    }

    @Test
    void checkReadReviewSuccessfully() {
        OrderRental orderRental = getOrderInSession();

        OrderRental foundOrder = session.get(OrderRental.class, orderRental.getId());

        assertNotNull(orderRental.getId());
        assertEquals(orderRental.getUser().getId(), foundOrder.getUser().getId());
        assertEquals(orderRental.getCar().getId(), orderRental.getCar().getId());
    }

    @Test
    void checkUpdateReviewSuccessfully() {
        OrderRental orderInSession = getOrderInSession();

        orderInSession.setStatus(OrderStatus.CANCELLED);
        orderInSession.setRentalEndTime(LocalDateTime.of(2024, 12, 26, 10, 15));
        orderInSession.setTotalRentalCost(getCar1().getRentalPrice());
        session.update(orderInSession);
        session.flush();
        OrderRental updateOrderRental = session.get(OrderRental.class, orderInSession.getId());

        assertNotNull(updateOrderRental);
        assertEquals(OrderStatus.CANCELLED, updateOrderRental.getStatus());
        assertEquals(LocalDateTime.of(2024, 12, 26, 10, 15), updateOrderRental.getRentalEndTime());
        assertEquals(getCar1().getRentalPrice(), updateOrderRental.getTotalRentalCost());
    }

    @Test
    void checkDeleteReviewSuccessfully() {
        OrderRental orderRental = getOrderInSession();

        session.delete(orderRental);
        session.flush();
        OrderRental deletedOrder = session.get(OrderRental.class, orderRental.getId());

        assertNull(deletedOrder);
    }

    private OrderRental getOrderInSession() {
        User user1 = getUser1();
        Car car1 = getCar1();
        OrderRental orderRental = getOrderRentalInApplication();
        user1.addOrderRental(orderRental);
        car1.addOrderRental(orderRental);

        session.persist(user1);
        session.persist(car1);
        session.flush();
        session.persist(orderRental);
        session.flush();
        return orderRental;
    }

    private OrderRental getOrderRentalInApplication() {
        return OrderRental.builder()
                .user(getUser1())
                .car(getCar1())
                .rentalStartTime(LocalDateTime.of(2024, 12, 25, 10, 15))
                .rentalEndTime(null)
                .totalRentalCost(null)
                .status(OrderStatus.CONFIRMED)
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
