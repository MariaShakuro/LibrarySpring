package jwtSecurity.example.jwtdemo.config;
/*
import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpServletResponse;
import jwtSecurity.example.jwtdemo.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.DisplayName;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@ActiveProfiles("test")
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
*/
