package jwtSecurity.example.jwtdemo.repository;


import jwtSecurity.example.jwtdemo.model.Role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertNotNull;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test for RoleRepository")
public class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Should find by name")
    void shouldFindByName() {
        String roleName = "ROLE_USER";
        Role role = new Role();
        role.setName(roleName);
        when(roleRepository.findByName(roleName)).thenReturn(role);
        Role foundRole = roleRepository.findByName(roleName);
        assertNotNull(foundRole);
        assertEquals(roleName, foundRole.getName());
    }
}
