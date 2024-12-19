package jwtSecurity.example.jwtDemo.Controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jwtSecurity.example.jwtDemo.Config.JwtTokenProvider;
import jwtSecurity.example.jwtDemo.Dto.LoginDto;
import jwtSecurity.example.jwtDemo.Service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private AuthService authService;


    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testuser");
        loginDto.setPassword("password");

        String token = "mock-token";
        when(authService.login(any(LoginDto.class))).thenReturn(token);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(token));
    }

    @Test
    void testValidateToken() throws Exception {
        String token = "Bearer mock-token";
        when(jwtTokenProvider.validateToken("mock-token")).thenReturn(true);

        mockMvc.perform(get("/api/auth/validate")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }
}
