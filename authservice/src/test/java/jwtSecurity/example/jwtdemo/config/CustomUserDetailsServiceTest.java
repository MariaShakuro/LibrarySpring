package jwtSecurity.example.jwtdemo.config;

import java.util.Optional;
import java.util.Set;

import jwtSecurity.example.jwtdemo.model.Role;
import jwtSecurity.example.jwtdemo.model.User;
import jwtSecurity.example.jwtdemo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.junit.jupiter.api.DisplayName;

import java.util.HashSet;
import java.util.stream.Collectors;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Unit Tests for CustomUserDetailsService")
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("Should Load User By Username")
    void shouldLoadUserByUsername() {
        String username = "testuser";
        String password = "testpassword";

        Role roleUser = Role.builder().name("ROLE_USER").build();
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        User user = User.builder()
                .username(username)
                .password(password)
                .roles(roles)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getPassword()).isEqualTo(password);
        Set<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        assertThat(authorities).contains("ROLE_USER");
    }

    @Test
    @DisplayName("Should Throw Exception When User Not Found")
    void shouldThrowExceptionWhenUserNotFound() {
        String username = "unknownuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(username);
        });
    }
}


