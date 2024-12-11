package com.example.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final
    //Берем пользователя из бд->сверяем пароль->если юзер валиден,то даем токен,нет-ошибка
    public Mono<TokenDetails>authenticate(String username ,String password)
}
