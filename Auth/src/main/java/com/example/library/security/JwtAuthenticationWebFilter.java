package com.example.library.security;

import org.modelmapper.internal.asm.tree.ModuleNode;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.lang.management.MonitorInfo;

public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    public JwtAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager,JwtHandler jwtHandler) {
        super(authenticationManager);
        this.setServerAuthenticationConverter(new BearerTokenServerAuthenticationConverter(jwtHandler));
    }
    @Override
    public Mono<Void>filter(ServerWebExchange exchange, WebFilterChain chain){
        return super.filter(exchange,chain);
    }
}
