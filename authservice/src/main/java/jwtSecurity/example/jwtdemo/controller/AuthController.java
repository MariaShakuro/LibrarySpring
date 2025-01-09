package jwtSecurity.example.jwtdemo.controller;


import jwtSecurity.example.jwtdemo.config.JwtTokenProvider;
import jwtSecurity.example.jwtdemo.dto.AuthResponseDto;
import jwtSecurity.example.jwtdemo.dto.LoginDto;
import jwtSecurity.example.jwtdemo.dto.RegisterDto;
import jwtSecurity.example.jwtdemo.service.AuthService;
import jwtSecurity.example.jwtdemo.exception.AccessDeniedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final String REGISTRATION_SUCCESS_MESSAGE = "Congratulations on successful registration";
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        try {
            String registeredUser = authService.register(registerDto);
            return new ResponseEntity<>(REGISTRATION_SUCCESS_MESSAGE, HttpStatus.CREATED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        boolean isValid = jwtTokenProvider.validateToken(token.replace("Bearer ", "").trim());
        return ResponseEntity.ok(isValid);
    }
}
