package jwtSecurity.example.jwtDemo.Repository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jwtSecurity.example.jwtDemo.Model.Role;
import jwtSecurity.example.jwtDemo.Repository.RoleRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testSaveAndFindById() {
        Role role = new Role();
        role.setName("ROLE_USER");

        Role savedRole = roleRepository.save(role);

        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getId()).isNotNull();
        assertThat(savedRole.getName()).isEqualTo("ROLE_USER");


        Role foundRole = roleRepository.findById(savedRole.getId()).orElse(null);

        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getId()).isEqualTo(savedRole.getId());
        assertThat(foundRole.getName()).isEqualTo("ROLE_USER");
    }
}
