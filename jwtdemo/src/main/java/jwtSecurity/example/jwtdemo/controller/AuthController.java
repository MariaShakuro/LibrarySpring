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
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto){
        //01 - Receive the token from AuthService
        String token = authService.login(loginDto);
        //02 - Set the token as a response using JwtAuthResponse Dto class
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);
        //03 - Return the response to the user
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<String>register(@Valid @RequestBody RegisterDto registerDto){
        try {
            String registeredUser = authService.register(registerDto);
            return new ResponseEntity<>("Поздравляем с успешной регистрацией", HttpStatus.CREATED);
        }catch(AccessDeniedException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
         log.debug("Received token: {}",token);
        boolean isValid = jwtTokenProvider.validateToken(token.replace("Bearer ","").trim());
        log.debug("Is token valid: {}", isValid);
        return ResponseEntity.ok(isValid);
    }
}
