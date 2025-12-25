package com.example.demo.service.impl;

import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    // THIS METHOD WAS MISSING OR HAD WRONG NAME
    public User register(User user) {
        if (user == null) throw new RuntimeException("User is null");
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.prePersist(); // Set the createdAt timestamp
        return userRepository.save(user);
    }

    // THIS METHOD WAS MISSING OR HAD WRONG NAME
    public AuthResponse login(String email, String password) {
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!encoder.matches(password, u.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", u.getRole());
        claims.put("userId", u.getId());

        String token = jwtUtil.generateToken(claims, email);

        return AuthResponse.builder()
                .accessToken(token)
                .userId(u.getId())
                .email(email)
                .role(u.getRole())
                .build();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}