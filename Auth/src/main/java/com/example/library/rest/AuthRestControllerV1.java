package com.example.library.rest;

import com.example.library.dto.AuthRequestDTO;
import com.example.library.dto.AuthResponseDTO;
import com.example.library.dto.UserDTO;
import com.example.library.entity.UserEntity;
import com.example.library.mapper.UserMapper;
import com.example.library.repository.UserRepository;
import com.example.library.security.CustomPricipal;
import com.example.library.security.SecurityService;
import com.example.library.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {
  private  final SecurityService securityService;
  private final UserService userService;
  private final UserMapper userMapper;
  @Autowired
  public AuthRestControllerV1(SecurityService securityService,UserService userService,
                              UserMapper userMapper){
      this.securityService=securityService;
      this.userService=userService;
      this.userMapper=userMapper;
  }
  @PostMapping("/register")
  public Mono<UserDTO>register(@RequestBody UserDTO userDTO){
      UserEntity entity=userMapper.map(userDTO);
      return userService.register(entity)
              .map(userMapper::map);
  }
  @PostMapping("/login")
  public Mono<AuthResponseDTO>login(@RequestBody AuthRequestDTO dto){
   return securityService.authenticate(dto.getUsername(),dto.getPassword())
           .flatMap(tokenDetails -> Mono.just(
                   AuthResponseDTO.builder()
                           .userId(tokenDetails.getUserId())
                           .token(tokenDetails.getToken())
                           .issuedAt(tokenDetails.getIssuedAt())
                           .expiredAt(tokenDetails.getExpiredAt())
                           .build()
           ));
  }
  @GetMapping("/info")
  public Mono<UserDTO>getUserInfo(Authentication authentication){
      CustomPricipal customPricipal=(CustomPricipal) authentication.getPrincipal();
      return userService.getUserById(customPricipal.getId())
              .map(userMapper::map);
  }
}
