package jwtSecurity.example.jwtdemo.controller;

import jwtSecurity.example.jwtdemo.BaseTest;
import jwtSecurity.example.jwtdemo.dto.LoginDto;
import jwtSecurity.example.jwtdemo.dto.RegisterDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration Tests for AuthController")
public class AuthControllerIntegrationTest extends BaseTest {


    @Test
    @DisplayName("Should Login Successfully")
    void shouldLoginSuccessfully() throws Exception {
        LoginDto loginDto = LoginDto.builder()
                .username("testuser")
                .password("testpassword")
                .build();


        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }

    @Test
    @DisplayName("Should Register Successfully")
    void shouldRegisterSuccessfully() throws Exception {
        RegisterDto registerDto = RegisterDto.builder()
                .username("newuser")
                .password("newpassword")
                .email("newuser@example.com")
                .build();

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Congratulations on successful registration"));
    }


}
