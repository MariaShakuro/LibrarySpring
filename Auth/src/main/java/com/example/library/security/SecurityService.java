package com.example.library.security;

import com.example.library.Exception.AuthException;
import com.example.library.entity.UserEntity;
import com.example.library.repository.UserRepository;
import com.example.library.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static io.jsonwebtoken.SignatureAlgorithm.*;

@Component
@RequiredArgsConstructor
public class SecurityService {
    //Token:Header,Payload,Signature
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationInSeconds}")
    private Integer expirationInSeconds;
    @Value("${jwt.issuer}")
    private String issuer;

    private TokenDetails generateToken(UserEntity user){
        Map<String,Object>claims=new HashMap<>(){{
            put("role",user.getRole());
            put("username",user.getUsername());
        }};
        return generateToken(claims,user.getId().toString());
    }
    private TokenDetails generateToken(Map<String,Object>claims,String subject){
        Long expirationTimeInMillis=expirationInSeconds*1000L;
        Date expirationDate=new Date(new Date().getTime()+expirationTimeInMillis);
        return generateToken(expirationDate,claims,subject);
    }
    private TokenDetails generateToken(Date expirationDate, Map<String,Object>claims,String subject){
        Date createdDate=new Date();
        String token= Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setId(UUID.randomUUID().toString())
                .setExpiration(expirationDate)
                .signWith(HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();
        return TokenDetails.builder()
                .token(token)
                .issuedAt(createdDate)
                .expiredAt(expirationDate)
                .build();
    }
    //Берем пользователя из бд->сверяем пароль->если юзер валиден,то даем токен,нет-ошибка
    public Mono<TokenDetails> authenticate(String username , String password){
        return userService.getUserByUsername(username)
                .flatMap(user->{
                   if(!user.isEnabled()) return Mono.error(new AuthException("Account disabled","PROSELYTE_USER_ACCOUNT_DISABLED"));
                   if(!passwordEncoder.matches(password,user.getPassword()))return Mono.error(new AuthException("Invalid password","PROSELYTE_INVALID_PASSWORD"));

                   return Mono.just(generateToken(user).toBuilder()
                                   .userId(user.getId())
                           .build());
                })
                .switchIfEmpty(Mono.error(new AuthException("Invalid username","PROSELYTE_INVALID_USERNAME")));
    }
}

