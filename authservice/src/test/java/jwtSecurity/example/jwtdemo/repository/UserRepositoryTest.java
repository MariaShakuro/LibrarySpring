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

    @Test
    @DisplayName("Should Find User By Username")
    void shouldFindByUsername() {
        String username = "testuser";
        User user=User.builder()
                .username(username)
                .build();
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByUsername(username);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("Should Find User By Email")
    void shouldFindByEmail() {
        String email = "testuser@example.com";
        User user=User.builder()
                .email(email)
                .build();
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByEmail(email);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(email);
    }
}
