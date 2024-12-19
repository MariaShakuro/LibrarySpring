package jwtSecurity.example.jwtDemo.Service.Impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jwtSecurity.example.jwtDemo.Config.JwtTokenProvider;
import jwtSecurity.example.jwtDemo.Dto.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private LoginDto loginDto;
    private Authentication authentication;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        loginDto = new LoginDto();
        loginDto.setUsername("testuser");
        loginDto.setPassword("password");

        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Test
    void testLogin_Success() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(any(Authentication.class))).thenReturn("mock-jwt-token");

        String token = authService.login(loginDto);

        assertNotNull(token);
        assertEquals("mock-jwt-token", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, times(1)).generateToken(any(Authentication.class));
    }

    @Test
    void testLogin_Failure() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        assertThrows(RuntimeException.class, () -> {
            authService.login(loginDto);
        });

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, never()).generateToken(any(Authentication.class));
    }
}
