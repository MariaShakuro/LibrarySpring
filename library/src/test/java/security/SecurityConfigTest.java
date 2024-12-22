package security;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.library.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.library.LibraryApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LibraryApplication.class)
@TestPropertySource(properties = {
        "security.jwt.secret=2LApL4VfCAY82XnhfDQgZwE8Z+fPo3cO8z7pXyjGg7bB3ZyF3saMlxR9j+R2oCw2"
})
public class SecurityConfigTest {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    private final SecurityConfig securityConfig = new SecurityConfig();

    @Test
    void testJwtDecoder() {

        securityConfig.jwtSecret = jwtSecret;
        JwtDecoder decoder = securityConfig.jwtDecoder();

        assertNotNull(decoder);
        assertTrue(decoder instanceof NimbusJwtDecoder);
    }
}









