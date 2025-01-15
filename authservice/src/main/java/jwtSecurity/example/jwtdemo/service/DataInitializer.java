package jwtSecurity.example.jwtdemo.service;

import jakarta.annotation.PostConstruct;
import jwtSecurity.example.jwtdemo.model.Role;
import jwtSecurity.example.jwtdemo.model.User;
import jwtSecurity.example.jwtdemo.repository.RoleRepository;
import jwtSecurity.example.jwtdemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import jakarta.transaction.Transactional;


@Component
@Slf4j
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
        log.info("initializing data.....");
        Optional<Role> adminRoleOptional = Optional.ofNullable(roleRepository.findByName("ROLE_ADMIN"));
        Role adminRole;
        if (adminRoleOptional.isPresent()) {
            adminRole = adminRoleOptional.get();
        } else {
            adminRole = Role.builder()
                    .name("ROLE_ADMIN")
                    .build();
            roleRepository.save(adminRole);
            log.info("saved role:{}", adminRole);
        }
        Optional<Role> userRoleOptional = Optional.ofNullable(roleRepository.findByName("ROLE_USER"));
        Role userRole;
        if (userRoleOptional.isPresent()) {
            userRole = userRoleOptional.get();
        } else {
            userRole = Role.builder()
                    .name("ROLE_USER")
                    .build();
            roleRepository.save(userRole);
        }

        Optional<User> userOptional = userRepository.findByUsername("shakuro");
        User user;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = User.builder()
                    .email("masha@gmail.com")
                    .name("masha")
                    .username("shakuro")
                    .password(passwordEncoder.encode("123456"))
                    .build();
            userRepository.save(user);
            log.info("Saved user:{}", user);

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}





