package libraryservice.libraryservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = SecurityConfigTest.TestConfig.class)
@TestPropertySource(properties = {
        "security.jwt.secret=2LApL4VfCAY82XnhfDQgZwE8Z+fPo3cO8z7pXyjGg7bB3ZyF3saMlxR9j+R2oCw2"
})
public class SecurityConfigTest {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
        securityConfig.jwtSecret = jwtSecret;
    }

    @Test
    void testJwtDecoder() {
        JwtDecoder decoder = securityConfig.jwtDecoder();
        assertNotNull(decoder);
        assertTrue(decoder instanceof NimbusJwtDecoder);
    }


    @Configuration
    static class TestConfig {
        @Bean
        public SecurityConfig securityConfig() {
            return new SecurityConfig();
        }

        @Bean
        public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
            return new HandlerMappingIntrospector();
        }
    }
}




