package jwtSecurity.example.jwtdemo.config;

import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.DisplayName;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.Mock;


import org.springframework.security.core.AuthenticationException;


@ExtendWith(MockitoExtension.class)
@DisplayName("Test for JwtAuthenticationEntryPoint ")
public class JwtAuthenticationEntryPointTest {

    @InjectMocks
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @Test
    @DisplayName("Should commence with unauthorized error")
    void shouldCommenceWithUnauthorizedError() throws Exception {
        String errorMessage = "Unauthorized error";
        when(authException.getMessage()).thenReturn(errorMessage);

        jwtAuthenticationEntryPoint.commence(request, response, authException);

        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, errorMessage);
    }
}

