package jwtSecurity.example.jwtdemo.service.Impl;

/*
import jwtSecurity.example.jwtdemo.BaseTest;
import jwtSecurity.example.jwtdemo.config.JwtTokenProvider;
import jwtSecurity.example.jwtdemo.dto.LoginDto;
import jwtSecurity.example.jwtdemo.dto.RegisterDto;
import jwtSecurity.example.jwtdemo.exception.AccessDeniedException;
import jwtSecurity.example.jwtdemo.model.Role;
import jwtSecurity.example.jwtdemo.model.User;
import jwtSecurity.example.jwtdemo.repository.RoleRepository;
import jwtSecurity.example.jwtdemo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ActiveProfiles("test")
@DisplayName("Integration Tests for AuthServiceImpl")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthServiceImplIntegrationTest extends BaseTest {

    UserRepository userRepository;
    RoleRepository roleRepository;
    JwtTokenProvider jwtTokenProvider;

    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        authService = new AuthServiceImpl(authenticationManager, jwtTokenProvider, passwordEncoder, userRepository, roleRepository);


        Role userRole = Role.builder()
                .name("ROLE_USER")
                .build();
        roleRepository.save(userRole);
    }

    @Test
    @DisplayName("Should Login Successfully")
    void shouldLoginSuccessfully() {
        User user = User.builder()
                .username("testuser")
                .password(passwordEncoder.encode("testpassword"))
                .build();
        userRepository.save(user);

        LoginDto loginDto = LoginDto.builder()
                .username("testuser")
                .password("testpassword")
                .build();

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(any())).thenReturn("mocked-token");

        String token = authService.login(loginDto);

        assertThat(token).isEqualTo("mocked-token");
    }

    @Test
    @DisplayName("Should Register Successfully")
    void shouldRegisterSuccessfully() {
        RegisterDto registerDto = RegisterDto.builder()
                .username("newuser")
                .password("newpassword")
                .email("newuser@example.com")
                .build();

        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(roleRepository.findByName("ROLE_USER"));
        when(jwtTokenProvider.generateToken(any())).thenReturn("mocked-token");

        String token = authService.register(registerDto);

        User savedUser = userRepository.findByUsername("newuser").orElseThrow();
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("newuser@example.com");
        assertThat(token).isEqualTo("mocked-token");
    }

    @Test
    @DisplayName("Should Throw Exception When User Already Registered")
    void shouldThrowExceptionWhenUserAlreadyRegistered() {
        User existingUser = User.builder()
                .email("existinguser@example.com")
                .build();
        userRepository.save(existingUser);

        RegisterDto registerDto = RegisterDto.builder()
                .email("existinguser@example.com")
                .build();

        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.of(existingUser));

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
            authService.register(registerDto);
        });

        assertThat(exception.getMessage()).isEqualTo("You are already registered");
    }

    @Test
    @DisplayName("Should Throw Exception When Role Not Found")
    void shouldThrowExceptionWhenRoleNotFound() {
        RegisterDto registerDto = RegisterDto.builder()
                .username("newuser")
                .password("newpassword")
                .email("newuser@example.com")
                .build();

        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(registerDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Role USER not found");
    }
}


*/


