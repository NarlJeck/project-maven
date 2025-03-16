package com.narel;

import com.narel.entity.Car;
import com.narel.enums.CarStatus;
import com.narel.enums.Type;
import com.narel.repository.CarRepository;
import com.narel.util.HibernateUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

@Slf4j
public class HibernateRunner {

    @Transactional
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            session.beginTransaction();
//
            CarRepository carRepository = new CarRepository(session);
            Car build = Car.builder()
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
            carRepository.save(build);
            session.getTransaction().commit();
        }

    }
}

