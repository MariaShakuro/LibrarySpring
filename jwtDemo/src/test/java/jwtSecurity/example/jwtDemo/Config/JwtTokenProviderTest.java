package jwtSecurity.example.jwtDemo.Config;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    void testGenerateToken() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        when(authentication.getAuthorities()).thenReturn(
                Stream.of(new SimpleGrantedAuthority("ROLE_USER")).collect(Collectors.toList()));

        String token = jwtTokenProvider.generateToken(authentication);

        assertNotNull(token);

        Claims claims = Jwts.parser()
                .setSigningKey(jwtTokenProvider.key())
                .parseClaimsJws(token)
                .getBody();

        assertEquals("testuser", claims.getSubject());
        List<String> roles = claims.get("roles", List.class);
        assertTrue(roles.contains("ROLE_USER"));
    }

    @Test
    void testGetUsername() {
        String token = jwtTokenProvider.generateToken(mock(Authentication.class));

        String username = jwtTokenProvider.getUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    void testValidateToken() {
        String token = jwtTokenProvider.generateToken(mock(Authentication.class));

        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void testValidateToken_InvalidToken() {
        assertFalse(jwtTokenProvider.validateToken("invalid-token"));
    }
}
