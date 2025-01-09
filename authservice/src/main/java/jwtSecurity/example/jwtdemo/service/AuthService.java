package jwtSecurity.example.jwtdemo.service;

import jwtSecurity.example.jwtdemo.dto.LoginDto;
import jwtSecurity.example.jwtdemo.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}