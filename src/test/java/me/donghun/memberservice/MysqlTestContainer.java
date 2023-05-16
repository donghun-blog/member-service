package me.donghun.memberservice;

import me.donghun.memberservice.adapter.out.s3.config.S3Config;
import me.donghun.memberservice.adapter.out.s3.config.S3Properties;
import me.donghun.memberservice.filters.IntegrationTest;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test-container")
public class MysqlTestContainer extends IntegrationTest {
    @Container
    protected static final MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0.30")
            .withUsername("test")
            .withPassword("test");

    @MockBean
    protected S3Properties s3Properties;

    @MockBean
    protected S3Config s3Config;
}
