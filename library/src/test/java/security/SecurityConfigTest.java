package security;

import com.example.library.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void testUserDetailsService() throws Exception {
        String username = "shakuro";
        String password = "121212";
        String role = "USER";

        UserDetailsService userDetailsService = new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username(username)
                        .password(password)
                        .roles(role)
                        .build()
        );

        assertNotNull(userDetailsService.loadUserByUsername(username));
    }

    @Test
    @WithMockUser(username = "shakuro", password = "121212", roles = "USER")
    public void testAuthorizedAccess() throws Exception {
        mockMvc.perform(formLogin().user("shakuro").password("121212"))
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername("shakuro"));
    }

    @Test
    public void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(formLogin().user("invalid").password("invalid"))
                .andExpect(status().isUnauthorized())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser(username = "shakuro", password = "121212", roles = "USER")
    public void testLogout() throws Exception {
        mockMvc.perform(logout())
                .andExpect(status().isOk())
                .andExpect(unauthenticated());
    }
}
