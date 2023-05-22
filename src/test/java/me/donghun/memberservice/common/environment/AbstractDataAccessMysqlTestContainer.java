package me.donghun.memberservice.common.environment;

import me.donghun.memberservice.config.QueryDslTestConfig;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test-container")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@Import(QueryDslTestConfig.class)
public abstract class AbstractDataAccessMysqlTestContainer extends AbstractIntegrationTest {
    @Container
    protected static final MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0.30")
            .withUsername("test")
            .withPassword("test");
}
