package com.example.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebClientConfig implements WebMvcConfigurer {

    @Bean
    public WebClient webClient(Long id){
        return WebClient.builder().build();
    }
}
