package jwtSecurity.example.jwtdemo.config;
/*
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import jwtSecurity.example.jwtdemo.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Unit Tests for SecurityConfig")
public class SecurityConfigTest extends BaseTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    @DisplayName("Should Return Password Encoder Bean")
    void shouldReturnPasswordEncoderBean() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertThat(passwordEncoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    @DisplayName("Should Return Authentication Manager Bean")
    void shouldReturnAuthenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager = securityConfig.authenticationManager(new AuthenticationConfiguration());
        assertThat(authenticationManager).isNotNull();
    }

    @Test
    @DisplayName("Should Return JwtAuthenticationFilter Bean")
    void shouldReturnJwtAuthenticationFilterBean() {
        JwtAuthenticationFilter jwtAuthenticationFilter = securityConfig.jwtAuthenticationFilter(jwtTokenProvider);
        assertThat(jwtAuthenticationFilter).isNotNull();
    }
}
*/

