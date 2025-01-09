package jwtSecurity.example.jwtdemo;

import jakarta.annotation.PostConstruct;
import jwtSecurity.example.jwtdemo.model.Role;
import jwtSecurity.example.jwtdemo.model.User;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @PostConstruct
    public void init() {

        Role adminRole = Role.builder()
                .id(1L)
                .name("ROLE_ADMIN")
                .build();
        entityManager.persist(adminRole);

        Role userRole = Role.builder()
                .id(2L)
                .name("ROLE_USER")
                .build();
        entityManager.persist(userRole);

        User user = User.builder()
                .id(1L)
                .email("masha@gmail.com")
                .name("masha")
                .username("shakuro")
                .password("123456")
                .build();
        entityManager.persist(user);


        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        user.setRoles(roles);
        entityManager.persist(user);
    }
}




