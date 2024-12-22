package jwtSecurity.example.jwtDemo.Config;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Collection;
import java.util.Arrays;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKey;


@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    void testGenerateToken() throws Exception {
        Authentication authentication = mock(Authentication.class);
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getName()).thenReturn("testuser");
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        String token = jwtTokenProvider.generateToken(authentication);

        assertNotNull(token);

        Method keyMethod = JwtTokenProvider.class.getDeclaredMethod("key");
        keyMethod.setAccessible(true);
        SecretKey key = (SecretKey) keyMethod.invoke(jwtTokenProvider);

        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("testuser", claims.getSubject());
        List<String> roles = claims.get("roles", List.class);
        assertTrue(roles.contains("ROLE_USER"));
    }

    @Test
    void testGetUsername() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        String token = jwtTokenProvider.generateToken(authentication);

        String username = jwtTokenProvider.getUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    void testValidateToken() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        String token = jwtTokenProvider.generateToken(authentication);

        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void testValidateToken_InvalidToken() {
        assertFalse(jwtTokenProvider.validateToken("invalid-token"));
    }
}


