package jwtSecurity.example.jwtdemo;
import jakarta.annotation.PostConstruct;
import jwtSecurity.example.jwtdemo.model.Role;
import jwtSecurity.example.jwtdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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

        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setName("ROLE_ADMIN");

        Role userRole = new Role();
        userRole.setId(2L);
        userRole.setName("ROLE_USER");
        entityManager.persist(adminRole);
        entityManager.persist(userRole);

        User user = new User();
        user.setId(1L);
        user.setEmail("masha@gmail.com");
        user.setName("masha");
        user.setUsername("shakuro");
        user.setPassword("123456");
        entityManager.persist(user);


        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        user.setRoles(roles);
        entityManager.persist(user);
    }
}




