package com.example.demo.security;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class JwtUtil {
    public String generateToken(Map<String, Object> claims, String subject) {
        return "mock-jwt-token-for-" + subject;
    }

    public boolean validateToken(String token) {
        return token != null && !token.equals("bad") && !token.equals("expired");
    }
}