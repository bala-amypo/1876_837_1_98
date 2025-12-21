package com.example.demo.service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;

public interface UserService {

    User register(RegisterRequest request);

    AuthResponse login(String email, String password);

    User findById(Long id);

    User findByEmail(String email);
}
