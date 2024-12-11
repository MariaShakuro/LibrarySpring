package com.example.library.controller;

import com.example.library.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class ApiController {
    @Autowired
    private WebClient webClient;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @GetMapping("/api/data")
    public String getData() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());
        return webClient.get() .uri("http://example.com/api/data") .headers(headers -> headers.setBearerAuth(token)) .retrieve() .bodyToMono(String.class) .block();
    }
}
