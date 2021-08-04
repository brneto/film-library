package ie.ait.agile.filmlibrary;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static java.util.Collections.singletonMap;

public class MySqlTestContainer {

    private static final DockerImageName mysqlImage =
            DockerImageName.parse("mysql").withTag("8.0.12");

    private static final MySQLContainer<?> mysqlContainer =
            new MySQLContainer<>(mysqlImage)
                    .withDatabaseName("filmdb")
                    .withUrlParam("TC_DAEMON", "true")
                    .withTmpFs(singletonMap("/test_tmpfs", "rw"))
                    .withReuse(true);
    
    // https://github.com/testcontainers/testcontainers-java/issues/2352
    static {
        mysqlContainer.start();
    }

    @DynamicPropertySource
    static void setDataSourceUrl(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.initialization-mode", () -> "never");
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }
}
