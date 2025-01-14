package jwtSecurity.example.jwtdemo.config;
/*
import java.util.Collection;
import java.util.List;
import jwtSecurity.example.jwtdemo.BaseTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Unit Tests for JwtTokenProvider")
public class JwtTokenProviderTest extends BaseTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider.jwtSecret = jwtSecret;
    }

    @Test
    @DisplayName("Should Get Username from Token")
    void shouldGetUsernameFromToken() {
        String token = jwtTokenProvider.generateToken(authentication);

        String username = jwtTokenProvider.getUsername(token);

        assertThat(username).isEqualTo(authentication.getName());
    }

    @Test
    @DisplayName("Should Validate Token Successfully")
    void shouldValidateTokenSuccessfully() {
        String token = jwtTokenProvider.generateToken(authentication);

        boolean isValid = jwtTokenProvider.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should Not Validate Invalid Token")
    void shouldNotValidateInvalidToken() {
        String invalidToken = "invalid.token";

        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should Get Token from Request")
    void shouldGetTokenFromRequest() {
        String bearerToken = "Bearer valid.token";
        when(request.getHeader("Authorization")).thenReturn(bearerToken);

        String token = jwtTokenProvider.getTokenFromRequest(request);

        assertThat(token).isEqualTo("valid.token");
    }

    @Test
    @DisplayName("Should Return Null if Token Not Present in Request")
    void shouldReturnNullIfTokenNotPresentInRequest() {
        when(request.getHeader("Authorization")).thenReturn(null);

        String token = jwtTokenProvider.getTokenFromRequest(request);

        assertThat(token).isNull();
    }
}

*/

