package jwtSecurity.example.jwtDemo.Controller;


import jakarta.validation.Valid;
import jwtSecurity.example.jwtDemo.Config.JwtTokenProvider;
import jwtSecurity.example.jwtDemo.Dto.AuthResponseDto;
import jwtSecurity.example.jwtDemo.Dto.LoginDto;
import jwtSecurity.example.jwtDemo.Dto.RegisterDto;
import jwtSecurity.example.jwtDemo.Model.User;
import jwtSecurity.example.jwtDemo.Service.AuthService;
import jwtSecurity.example.jwtDemo.exception.DuplicateException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


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
        }catch(DuplicateException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
         logger.debug("Received token: {}",token);
        boolean isValid = jwtTokenProvider.validateToken(token.replace("Bearer ","").trim());
        logger.debug("Is token valid: {}", isValid);
        return ResponseEntity.ok(isValid);
    }
}
