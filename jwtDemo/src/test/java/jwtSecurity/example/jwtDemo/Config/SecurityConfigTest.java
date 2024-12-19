package jwtSecurity.example.jwtDemo.Config;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"USER"})
    void testAuthenticatedAccess() throws Exception {
        mockMvc.perform(get("/api/protected-endpoint"))
                .andExpect(status().isOk());
    }

    @Test
    void testPublicAccess() throws Exception {
        mockMvc.perform(get("/api/auth/login"))
                .andExpect(status().isOk());
    }

    @Test
    void testUnauthenticatedAccess() throws Exception {
        mockMvc.perform(get("/api/protected-endpoint"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAuthenticationManagerBean() throws Exception {
        assertThat(mockMvc).isNotNull();
    }
}
