package com.narel.config;

import com.narel.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
@ComponentScan(basePackages = "com.narel")
public class ApplicationConfiguration {

    @Bean(destroyMethod = "close")
    public SessionFactory sessionFactory() {
        return HibernateUtil.buildSessionFactory();
    }

    @Bean
    public EntityManager entityManager() {
        return (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(sessionFactory().getCurrentSession(), args1));
    }
}

