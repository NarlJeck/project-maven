package config;

import org.postgresql.jdbc2.optional.ConnectionPool;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@TestConfiguration
public class ApplicationTestConfiguration {

    @MockitoSpyBean(name = "pool1")
    private ConnectionPool pool1;
}
