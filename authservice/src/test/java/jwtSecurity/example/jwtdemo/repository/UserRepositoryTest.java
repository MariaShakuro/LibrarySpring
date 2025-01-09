package jwtSecurity.example.jwtdemo.repository;


import jwtSecurity.example.jwtdemo.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Unit Tests for UserRepository")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRepositoryTest {

    UserRepository userRepository;
    User user;

    @BeforeEach
    void setUp() {
        User.builder()
                .username("testuser")
                .email("testuser@example.com")
                .build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("Should Find User By Username")
    void shouldFindByUsername() {
        String username = "testuser";
        Optional<User> foundUser = userRepository.findByUsername(username);

        assertTrue(foundUser.isPresent(), "User should be found");
        assertThat(foundUser.get().getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("Should Find User By Email")
    void shouldFindByEmail() {
        String email = "testuser@example.com";
        Optional<User> foundUser = userRepository.findByEmail(email);

        assertTrue(foundUser.isPresent(), "User should be found");
        assertThat(foundUser.get().getEmail()).isEqualTo(email);
    }
}
