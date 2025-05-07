package com.narel.spring.config;

import com.narel.spring.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN","USER"})
public class IntegrationTestBase {

    public static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:17");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
