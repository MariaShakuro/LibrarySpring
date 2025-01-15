package jwtSecurity.example.jwtdemo.config;


import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;


@DisplayName("Unit Tests for SecurityConfig")
@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    @DisplayName("Should return password encoder bean")
    void shouldReturnPasswordEncoderBean() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }


}

