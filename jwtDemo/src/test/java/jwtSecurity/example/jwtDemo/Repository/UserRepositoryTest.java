package jwtSecurity.example.jwtDemo.Repository;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import jwtSecurity.example.jwtDemo.Model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("testuser");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }
}
