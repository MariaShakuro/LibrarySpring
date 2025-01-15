package jwtSecurity.example.jwtdemo.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jwtSecurity.example.jwtdemo.config.JwtTokenProvider;
import jwtSecurity.example.jwtdemo.dto.AuthResponseDto;
import jwtSecurity.example.jwtdemo.dto.LoginDto;
import jwtSecurity.example.jwtdemo.dto.RegisterDto;
import jwtSecurity.example.jwtdemo.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations related to user authentication and registration")
public class AuthController {

    private static final String REGISTRATION_SUCCESS_MESSAGE = "Congratulations on successful registration";
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "User Login", description = "Allows a user to login and obtain a JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);
        log.info("User logged in with token: {}", token);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "User Registration", description = "Allows a new user to register")
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        String registeredUser = authService.register(registerDto);
        log.info("User registered: {}", registeredUser);
        return new ResponseEntity<>(REGISTRATION_SUCCESS_MESSAGE, HttpStatus.CREATED);

    }

    @Operation(summary = "Validate Token", description = "Validates the provided JWT token")
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        boolean isValid = jwtTokenProvider.validateToken(token.replace("Bearer ", "").trim());
        log.info("Token validation result: {}", isValid);
        return ResponseEntity.ok(isValid);
    }
}
