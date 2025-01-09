package com.example.library.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authServiceApplication", url = "http://authservice:8083")
public interface JwtAuthClient {
    @GetMapping("/api/auth/validate")
    Boolean validateToken(@RequestHeader("Authorization") String token);
}

