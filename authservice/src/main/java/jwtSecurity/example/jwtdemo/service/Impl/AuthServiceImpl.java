package jwtSecurity.example.jwtdemo.service.Impl;

import jwtSecurity.example.jwtdemo.config.JwtTokenProvider;
import jwtSecurity.example.jwtdemo.dto.LoginDto;
import jwtSecurity.example.jwtdemo.dto.RegisterDto;
import jwtSecurity.example.jwtdemo.model.Role;
import jwtSecurity.example.jwtdemo.model.User;
import jwtSecurity.example.jwtdemo.repository.RoleRepository;
import jwtSecurity.example.jwtdemo.repository.UserRepository;
import jwtSecurity.example.jwtdemo.service.AuthService;
import jwtSecurity.example.jwtdemo.exception.AccessDeniedException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    private static final String USER_ALREADY_REGISTERED_MESSAGE = "You are already registered";

    AuthenticationManager authenticationManager;
    JwtTokenProvider jwtTokenProvider;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;

    @Override
    public String login(LoginDto loginDto) {
        log.info("Attempting login for user: {}", loginDto.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        log.info("Attempting registration for user: {}", registerDto.getUsername());
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new AccessDeniedException(USER_ALREADY_REGISTERED_MESSAGE);
        }

        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new RuntimeException("Role USER not found");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                registerDto.getUsername(), registerDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

}
