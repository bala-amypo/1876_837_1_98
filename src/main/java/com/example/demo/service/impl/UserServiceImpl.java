package com.example.demo.service.impl;

import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;

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

    public User register(User user) {
        if (user == null) throw new RuntimeException("User is null");
        if (userRepository.existsByEmail(user.getEmail())) throw new RuntimeException("Email exists");
        user.setPassword(encoder.encode(user.getPassword()));
        user.prePersist();
        return userRepository.save(user);
    }

    public AuthResponse login(String email, String password) {
        User u = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Not found"));
        if (!encoder.matches(password, u.getPassword())) throw new RuntimeException("Bad credentials");
        String token = jwtUtil.generateToken(new HashMap<>(), email);
        return AuthResponse.builder().accessToken(token).userId(u.getId()).email(email).role(u.getRole()).build();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}