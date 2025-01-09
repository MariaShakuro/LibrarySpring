package jwtSecurity.example.jwtdemo.repository;


import jwtSecurity.example.jwtdemo.model.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Unit Tests for RoleRepository")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleRepositoryTest {

    RoleRepository roleRepository;
    Role role;

    @BeforeEach
    void setUp() {
        Role.builder()
                .name("ROLE_USER")
                .build();
        roleRepository.save(role);
    }

    @Test
    @DisplayName("Should Find Role By Name")
    void shouldFindByName() {
        String roleName = "ROLE_USER";
        Role foundRole = roleRepository.findByName(roleName);

        assertNotNull(foundRole, "Role should be found");
        assertThat(foundRole.getName()).isEqualTo(roleName);
    }
}
