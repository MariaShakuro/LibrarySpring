package jwtSecurity.example.jwtdemo.service;

import jakarta.annotation.PostConstruct;
import jwtSecurity.example.jwtdemo.model.Role;
import jwtSecurity.example.jwtdemo.model.User;
import jwtSecurity.example.jwtdemo.repository.RoleRepository;
import jwtSecurity.example.jwtdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {

   @Autowired
    RoleRepository roleRepository;
   @Autowired
    UserRepository userRepository;
   @Autowired
    BCryptPasswordEncoder passwordEncoder;

     @Transactional
     @PostConstruct
    public void init() {

        Role adminRole = Role.builder()
                .name("ROLE_ADMIN")
                .build();
        roleRepository.save(adminRole);

        Role userRole = Role.builder()
                .name("ROLE_USER")
                .build();
        roleRepository.save(userRole);

        User user = User.builder()
                .email("masha@gmail.com")
                .name("masha")
                .username("shakuro")
                .password(passwordEncoder.encode("123456"))
                .build();
        userRepository.save(user);


        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);
    }
}





