package com.example.demo.service.impl;

import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Ensure this is imported
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder; // Changed from 'passwordEncoder' to 'encoder' to match your previous errors
    private final JwtUtil jwtUtil;

    // The Spring container will now find the bean defined in SecurityConfig
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }
    
    // ... register and login methods ...
}