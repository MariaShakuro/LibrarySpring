package com.example.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authentication.ServerX509AuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class BearerTokenServerAuthenticationConverter implements ServerAuthenticationConverter {
    private final JwtHandler jwtHandler;
    private static final String BEARER_PREFIX="Bearer ";
    private static final Function<String,Mono<String>>getBearerValue=authValue->Mono.justOrEmpty(authValue.substring(BEARER_PREFIX.length()));
    private static final List<String>PUBLIC_ROUTES=List.of("/api/v1/auth/register", "/api/v1/auth/login");
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String path=exchange.getRequest().getURI().getPath();
        // Пропускаем проверку токена для публичных маршрутов
        if (PUBLIC_ROUTES.contains(path)) {
            return Mono.empty();
        }
        return extractHeader(exchange)
                .flatMap(getBearerValue)
                .flatMap(jwtHandler::check)
                .flatMap(UserAuthenticationBearer::create);
    }
    private Mono<String>extractHeader(ServerWebExchange exchange){
        return Mono.justOrEmpty(exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION));
    }
}
