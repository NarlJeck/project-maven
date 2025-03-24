package config;

import com.narel.config.ApplicationConfiguration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import util.HibernateTestUtil;

@Configuration
@ComponentScan(basePackages = "com.narel",
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = ApplicationConfiguration.class))
public class ApplicationTestConfiguration {

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory){
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public SessionFactory hibernateTestUtil() {
        return HibernateTestUtil.buildSessionFactory();
    }

}
