package com.narel;

import com.narel.entity.User;
import com.narel.enums.Role;
import com.narel.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {
        User user = User.builder()
                .fullName("Narel Jeck")
                .phoneNumber(298980892)
                .email("narelJeck@gamil.com")
                .residentialAddress("Minsk")
                .role(Role.ADMIN)
                .passport("BM 1234124")
                .driverLicense("BM 2240")
                .bankCard("MasterCard")
                .password("2431v3d")
                .build();
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            try (session) {
                Transaction transaction = session.beginTransaction();
                log.info("Transaction created: {}", transaction);


                session.save(user);
                session.getTransaction().commit();

            }
        } catch (Exception exception) {
            log.error("Exception occurred: ", exception);
            throw exception;
        }


    }
}
