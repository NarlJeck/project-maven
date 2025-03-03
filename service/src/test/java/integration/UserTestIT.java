package integration;

import com.narel.entity.User;
import com.narel.enums.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTestIT {

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
    void checkCreateUserSuccessfully() {
        User user = getUser1();

        session.persist(user);
        session.flush();
        session.clear();

        assertNotNull(user.getId());
    }

    @Test
    void checkReadUserSuccessfully() {
        User user1 = getUser1();
        session.save(user1);
        session.flush();
        session.clear();

        User founderUser = session.get(User.class, user1.getId());

        assertNotNull(founderUser);
        assertEquals(user1.getFullName(), founderUser.getFullName());
        assertEquals(user1.getPhoneNumber(), founderUser.getPhoneNumber());
    }

    @Test
    void checkUpdateUserSuccessfully() {
        User user1 = getUser1();
        session.save(user1);
        session.flush();
        session.clear();
        user1.setResidentialAddress("Grodno");

        session.update(user1);
        session.flush();
        session.clear();

        User updateUser = session.get(User.class, user1.getId());
        assertNotNull(updateUser);
        assertEquals("Grodno", updateUser.getResidentialAddress());
    }

    @Test
    void checkDeleteUserSuccessfully() {
        User user1 = getUser1();
        session.save(user1);
        session.flush();
        session.clear();

        session.delete(user1);
        session.flush();
        session.clear();

        User deletedUser = session.get(User.class, user1.getId());
        assertNull(deletedUser);
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
