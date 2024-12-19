package jwtSecurity.example.jwtDemo.Config;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.io.IOException;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationEntryPointTest {

    @InjectMocks
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    void testCommence() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = mock(AuthenticationException.class);

        jwtAuthenticationEntryPoint.commence(request, response, authException);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
