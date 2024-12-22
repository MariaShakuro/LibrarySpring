package jwtSecurity.example.jwtDemo.Config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springdoc.core.GroupedOpenApi;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SwaggerConfigTest {

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(SwaggerConfig.class);
    }

    @Test
    void testCustomOpenAPI() {
        OpenAPI openAPI = context.getBean(OpenAPI.class);
        assertNotNull(openAPI);
        assertEquals("AuthService API", openAPI.getInfo().getTitle());
        assertEquals("1.0", openAPI.getInfo().getVersion());
        assertEquals("API documentation for AuthService", openAPI.getInfo().getDescription()); // Обновлено
        assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
        assertEquals("http://springdoc.org", openAPI.getInfo().getLicense().getUrl());
    }


    @Test
    void testPublicApi() {
        GroupedOpenApi groupedOpenApi = context.getBean(GroupedOpenApi.class);
        assertNotNull(groupedOpenApi);
        assertEquals("public", groupedOpenApi.getGroup());
        assertTrue(groupedOpenApi.getPathsToMatch().contains("/api/auth/**"));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public SwaggerConfig swaggerConfig() {
            return new SwaggerConfig();
        }

        @Bean
        public OpenAPI customOpenAPI() {
            return new SwaggerConfig().customOpenAPI();
        }

        @Bean
        public GroupedOpenApi publicApi() {
            return new SwaggerConfig().publicApi();
        }
    }
}
