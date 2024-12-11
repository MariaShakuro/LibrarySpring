package com.example.library.repository;

import com.example.library.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity,Long> {
    Mono<UserEntity>findByUsername(String username);
}
