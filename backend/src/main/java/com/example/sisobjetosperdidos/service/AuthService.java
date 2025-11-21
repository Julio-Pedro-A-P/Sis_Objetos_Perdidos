package com.example.sisobjetosperdidos.service;

import com.example.sisobjetosperdidos.dto.LoginRequest;
import com.example.sisobjetosperdidos.dto.RegisterRequest;
import com.example.sisobjetosperdidos.dto.JwtResponse;

public interface AuthService {
    JwtResponse login(LoginRequest request);
    String register(RegisterRequest request);
}
