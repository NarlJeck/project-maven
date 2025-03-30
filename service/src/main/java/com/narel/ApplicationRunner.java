package com.narel;

import com.narel.config.ApplicationConfiguration;
import com.narel.entity.Car;
import com.narel.enums.CarStatus;
import com.narel.enums.Type;
import com.narel.repository.CarRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
            CarRepository bean = context.getBean(CarRepository.class);
            System.out.println(bean.save(Car.builder()
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
                    .build()));
        }
    }
}
