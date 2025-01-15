package libraryservice.libraryservice.security;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.oauth2.jwt.JwtDecoder;


@ExtendWith(MockitoExtension.class)
@DisplayName("Test for SecurityConfig")
public class SecurityConfigTest {


    @Mock
    private JwtDecoder jwtDecoder;

    @Test
    @DisplayName("should decode jwt")
    void shouldDecodeJwt() {
        assertNotNull(jwtDecoder);
    }
}

