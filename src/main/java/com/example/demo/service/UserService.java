package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
    List<User> getAllUsers();
    boolean existsByEmail(String email);
    void deleteUser(Long id);
    Optional<User> findById(Long id);
}
