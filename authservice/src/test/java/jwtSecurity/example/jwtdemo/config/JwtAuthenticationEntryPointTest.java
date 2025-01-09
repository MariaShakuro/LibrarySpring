package jwtSecurity.example.jwtdemo.config;

import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpServletResponse;
import jwtSecurity.example.jwtdemo.BaseTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit Tests for JwtAuthenticationEntryPoint")
public class JwtAuthenticationEntryPointTest extends BaseTest {

    @InjectMocks
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    @DisplayName("Should Commence with Unauthorized Error")
    void shouldCommenceWithUnauthorizedError() throws Exception {
        String errorMessage = "Unauthorized error";
        when(authException.getMessage()).thenReturn(errorMessage);

        jwtAuthenticationEntryPoint.commence(request, response, authException);

        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, errorMessage);
    }
}

