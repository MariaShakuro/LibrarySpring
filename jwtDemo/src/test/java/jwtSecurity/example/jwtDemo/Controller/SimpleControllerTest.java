package jwtSecurity.example.jwtDemo.Controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(SimpleController.class)
@ExtendWith(SpringExtension.class)
public class SimpleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testHelloAdmin() throws Exception {
        mockMvc.perform(get("/api/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Admin"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testHelloUser() throws Exception {
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello User"));
    }

    @Test
    void testHelloAdmin_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testHelloUser_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isUnauthorized());
    }
}
