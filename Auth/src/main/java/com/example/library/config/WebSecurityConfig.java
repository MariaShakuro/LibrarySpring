package com.example.library.config;


import com.example.library.security.BearerTokenServerAuthenticationConverter;
import com.example.library.security.CustomReactiveAuthentificationManager;
import com.example.library.security.JwtAuthenticationWebFilter;
import com.example.library.security.JwtHandler;

import com.example.library.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;


@Configuration
@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${jwt.secret}")
    private String secret;
    private final String [] publicRoutes={"/api/v1/auth/register","/api/v1/auth/login"};
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, ReactiveAuthenticationManager
                                                         reactiveAuthenticationManager) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(publicRoutes).permitAll()
                        .anyExchange().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((exchange, ex) -> {
                            log.error("In SecurityWebFilterChain - unauthorized error: {}", ex.getMessage());
                            return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
                        })
                        .accessDeniedHandler((exchange, denied) -> {
                            log.error("In SecurityWebFilterChain - access denied: {}", denied.getMessage());
                            return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
                        })
                )
                 .addFilterAt(bearerAuthenticationFilter(reactiveAuthenticationManager),SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public AuthenticationWebFilter bearerAuthenticationFilter(ReactiveAuthenticationManager reactiveAuthenticationManager){
        AuthenticationWebFilter bearerAuthenticationFilter=new AuthenticationWebFilter(reactiveAuthenticationManager);
        bearerAuthenticationFilter.setServerAuthenticationConverter(new BearerTokenServerAuthenticationConverter(jwtHandler(secret)));
       /* bearerAuthenticationFilter.setRequiresAuthenticationMatcher(
                        ServerWebExchangeMatchers.pathMatchers("api/v1/auth/**")
        );*/
        return bearerAuthenticationFilter;
    }
    @Bean
    @Primary
    public ReactiveAuthenticationManager customReactiveAuthenticationManager(UserService userService) {
        return new CustomReactiveAuthentificationManager(userService);
    }
    @Bean
    public JwtHandler jwtHandler(@Value("${jwt.secret}") String secret) {
        return new JwtHandler(secret);
    }
    @Bean public ModelMapper modelMapper() { return new ModelMapper(); }
    @Bean public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
