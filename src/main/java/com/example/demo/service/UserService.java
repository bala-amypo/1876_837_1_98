package com.example.demo.service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import java.util.List;

public interface UserService {
    User registerUser(User user);
    AuthResponse loginUser(String email, String password);
    List<User> getAllUsers();
    User getUserById(Long id);
}
