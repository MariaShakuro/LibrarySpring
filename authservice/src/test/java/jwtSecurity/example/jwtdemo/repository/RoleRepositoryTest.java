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


    @Test
    @DisplayName("Should Find Role By Name")
    void shouldFindByName() {
        String roleName = "ROLE_USER";
        Role role=Role.builder()
                .name(roleName)
                .build();
        roleRepository.save(role);
        Role foundRole = roleRepository.findByName(roleName);

        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getName()).isEqualTo(roleName);
    }
}
