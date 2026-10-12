package id.my.hendisantika.accountingsample.integration;

import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.User;
import id.my.hendisantika.accountingsample.model.enums.UserRole;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Base class for integration tests using Testcontainers.
 * A single PostgreSQL container is started once and shared by all integration tests (and the cached
 * Spring context). Every request is authenticated as an OWNER of a test organization.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    private static final String TEST_ORG_EMAIL = "test-org@example.com";
    private static final String TEST_USER_EMAIL = "owner@example.com";

    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("accounting_test")
            .withUsername("test")
            .withPassword("test");

    static {
        postgres.start();
    }

    @Autowired
    protected OrganizationRepository organizationRepository;

    @Autowired
    protected UserRepository userRepository;

    protected Organization testOrganization;

    protected User testUser;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.flyway.enabled", () -> "true");

        // Keep the test database isolated from demo data
        registry.add("app.seed-data.enabled", () -> "false");

        // Disable Redis for tests
        registry.add("spring.cache.type", () -> "none");
        registry.add("spring.data.redis.repositories.enabled", () -> "false");

        // Disable mail for tests
        registry.add("spring.mail.host", () -> "localhost");
        registry.add("spring.mail.port", () -> "3025");

        // JWT settings for tests
        registry.add("jwt.secret", () -> "test-secret-key-that-is-long-enough-for-hs512-algorithm-minimum-512-bits");
        registry.add("jwt.expiration", () -> "3600000");
    }

    @BeforeEach
    void authenticateTestUser() {
        testOrganization = organizationRepository.findByEmail(TEST_ORG_EMAIL)
                .orElseGet(() -> {
                    Organization org = new Organization();
                    org.setName("Test Organization");
                    org.setEmail(TEST_ORG_EMAIL);
                    org.setPhone("1234567890");
                    org.setCurrencyCode("USD");
                    org.setTimeZone("UTC");
                    org.setFiscalYearStartMonth(1);
                    return organizationRepository.save(org);
                });

        testUser = userRepository.findByEmail(TEST_USER_EMAIL)
                .orElseGet(() -> userRepository.save(User.builder()
                        .organization(testOrganization)
                        .email(TEST_USER_EMAIL)
                        .passwordHash("not-used")
                        .firstName("Test")
                        .lastName("Owner")
                        .role(UserRole.OWNER)
                        .build()));
        testUser.setOrganization(testOrganization);

        TestSecurityContextHolder.setAuthentication(
                new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities()));
    }

    @AfterEach
    void clearAuthentication() {
        TestSecurityContextHolder.clearContext();
    }
}
