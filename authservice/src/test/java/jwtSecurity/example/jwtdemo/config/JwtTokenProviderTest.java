package jwtSecurity.example.jwtdemo.config;


import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Unit Tests for JwtTokenProvider")
@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Authentication authentication;

    @Mock
    private HttpServletRequest request;

    private String jwtSecret = "your-secret-key";

    @BeforeEach
    void setUp() {
        jwtTokenProvider.jwtSecret = jwtSecret;
    }

    @Test
    @DisplayName("Should not validate invalid token")
    void shouldNotValidateInvalidToken() {
        String invalidToken = "invalid.token";

        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should get token from request")
    void shouldGetTokenFromRequest() {
        String bearerToken = "Bearer valid.token";
        when(request.getHeader("Authorization")).thenReturn(bearerToken);

        String token = jwtTokenProvider.getTokenFromRequest(request);

        assertEquals("valid.token", token);
    }

    @Test
    @DisplayName("Should return null if token not present in request")
    void shouldReturnNullIfTokenNotPresentInRequest() {
        when(request.getHeader("Authorization")).thenReturn(null);

        String token = jwtTokenProvider.getTokenFromRequest(request);

        assertNull(token);
    }
}

