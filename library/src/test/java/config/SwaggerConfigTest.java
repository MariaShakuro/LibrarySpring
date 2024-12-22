package config;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.LibraryApplication;
import com.example.library.config.SwaggerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.GroupedOpenApi;

@SpringBootTest(classes = LibraryApplication.class)
@ExtendWith(SpringExtension.class)
public class SwaggerConfigTest {

    @InjectMocks
    private SwaggerConfig swaggerConfig;

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(SwaggerConfig.class);
    }

    @Test
    void testCustomOpenAPI() {
        OpenAPI openAPI = context.getBean(OpenAPI.class);
        assertNotNull(openAPI);
        assertEquals("Library API", openAPI.getInfo().getTitle());
        assertEquals("1.0", openAPI.getInfo().getVersion());
        assertEquals("API documentation for the Library", openAPI.getInfo().getDescription());
        assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
        assertEquals("http://springdoc.org", openAPI.getInfo().getLicense().getUrl());
    }

    @Test
    void testPublicApi() {
        GroupedOpenApi groupedOpenApi = context.getBean(GroupedOpenApi.class);
        assertNotNull(groupedOpenApi);
        assertEquals("public", groupedOpenApi.getGroup());
        assertTrue(groupedOpenApi.getPathsToMatch().contains("/api/books/**"));
    }
}
