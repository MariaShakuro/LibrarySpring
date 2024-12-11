package com.example.library.config;

import com.example.library.security.AuthenticationManager;
import com.example.library.security.BearerTokenServerAuthenticationConverter;
import com.example.library.security.JwtHandler;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
    @Value("${jwt.secret}")
    private String secret;

    private final String [] publicRoutes={"/api/v1/auth/register","/api/v1/auth/login"};
    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http, AuthenticationManager authenticationManager){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange->exchange
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(publicRoutes).permitAll()
                .anyExchange().authenticated()
                )
                .exceptionHandling(exceptions->exceptions
                .authenticationEntryPoint((swe,e)->{
                    log.error("In SecurityWebFilterChain - unauthorized error:{}",e.getMessage());
                    return Mono.fromRunnable(()->swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
                })
                .accessDeniedHandler((swe,e)->{
                    log.error("In SecurityWebFilterChain - access denied:{}",e.getMessage());
                    return Mono.fromRunnable(()->swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
                })
                )
                .addFilterAt(bearerAuthenticationFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();

    }
    @Bean
    public AuthenticationWebFilter bearerAuthenticationFilter(AuthenticationManager authenticationManager){
        AuthenticationWebFilter bearerAuthenticationFilter=new AuthenticationWebFilter(authenticationManager);
        bearerAuthenticationFilter.setServerAuthenticationConverter(new BearerTokenServerAuthenticationConverter(new JwtHandler(secret)));
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));
        return bearerAuthenticationFilter;
    }
    @Bean
    public JwtHandler jwtHandler(@Value("${jwt.secret}") String secret) {
        return new JwtHandler(secret);
    }


}
