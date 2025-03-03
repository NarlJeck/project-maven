package integration;

import com.narel.entity.Car;
import com.narel.enums.CarStatus;
import com.narel.enums.Type;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarTestIT {

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
        session.save(car1);
        session.flush();
        session.clear();

        Car founderCar = session.get(Car.class, car1.getId());

        assertNotNull(founderCar);
        assertEquals(car1.getRegistrationNumber(),founderCar.getRegistrationNumber());
        assertEquals(car1.getImage(),founderCar.getImage());
    }

    @Test
    void checkUpdateCarSuccessfully() {
        Car car1 = getCar1();
        session.save(car1);
        session.flush();
        session.clear();
        car1.setRentalPrice(70.0);

        session.update(car1);

        session.flush();
        session.clear();
        Car updatedCar = session.get(Car.class, car1.getId());
        assertNotNull(updatedCar);
        assertEquals(70.0, updatedCar.getRentalPrice());
    }

    @Test
    void checkDeleteCarSuccessfully() {
        Car car1 = getCar1();
        session.save(car1);
        session.flush();
        session.clear();

        session.delete(car1);
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
}
