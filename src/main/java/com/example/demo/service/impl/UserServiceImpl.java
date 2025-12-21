// // package com.example.demo.service.impl;

// // import com.example.demo.dto.AuthResponse;
// // import com.example.demo.dto.RegisterRequest;
// // import com.example.demo.exception.ResourceNotFoundException;
// // import com.example.demo.exception.ValidationException;
// // import com.example.demo.model.User;
// // import com.example.demo.repository.UserRepository;
// // import com.example.demo.security.JwtUtil;
// // import com.example.demo.service.UserService;
// // import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// // import org.springframework.stereotype.Service;
// // import org.springframework.transaction.annotation.Transactional;

// // import java.util.HashMap;
// // import java.util.Map;

// // @Service
// // @Transactional
// // public class UserServiceImpl implements UserService {

// //     private final UserRepository userRepository;
// //     private final BCryptPasswordEncoder passwordEncoder;
// //     private final JwtUtil jwtUtil;

// //     public UserServiceImpl(UserRepository userRepository,
// //                            BCryptPasswordEncoder passwordEncoder,
// //                            JwtUtil jwtUtil) {
// //         this.userRepository = userRepository;
// //         this.passwordEncoder = passwordEncoder;
// //         this.jwtUtil = jwtUtil;
// //     }

// //     // ✅ Register a new user
// //     @Override
// //     public User register(RegisterRequest request) {
// //         if (userRepository.existsByEmail(request.getEmail())) {
// //             throw new ValidationException("Email already registered");
// //         }

// //         User user = new User();
// //         user.setFullName(request.getFullName());
// //         user.setEmail(request.getEmail());
// //         user.setPassword(passwordEncoder.encode(request.getPassword()));
// //         user.setRole(request.getRole() != null ? request.getRole() : "LEARNER");
// //         user.setPreferredLearningStyle(request.getPreferredLearningStyle());

// //         return userRepository.save(user);
// //     }

// //     // ✅ Login user and return JWT token
// //     @Override
// //     public AuthResponse login(String email, String password) {
// //         User user = userRepository.findByEmail(email)
// //                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));

// //         if (!passwordEncoder.matches(password, user.getPassword())) {
// //             throw new ValidationException("Invalid password");
// //         }

// //         // Add claims to JWT
// //         Map<String, Object> claims = new HashMap<>();
// //         claims.put("userId", user.getId());
// //         claims.put("role", user.getRole());

// //         String token = jwtUtil.generateToken(claims, user.getEmail());

// //         // ✅ Fixed: Use builder instead of invalid constructor
// //         return AuthResponse.builder()
// //                 .accessToken(token)
// //                 .userId(user.getId())
// //                 .email(user.getEmail())
// //                 .role(user.getRole())
// //                 .build();
// //     }

// //     // ✅ Find user by ID
// //     @Override
// //     public User findById(Long id) {
// //         return userRepository.findById(id)
// //                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
// //     }

// //     // ✅ Find user by email
// //     @Override
// //     public User findByEmail(String email) {
// //         return userRepository.findByEmail(email)
// //                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
// //     }
// // }
// package com.example.demo.service.impl;

// import com.example.demo.dto.AuthResponse;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.exception.ValidationException;
// import com.example.demo.model.User;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.security.JwtUtil;
// import com.example.demo.service.UserService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.HashMap;
// import java.util.Map;

// @Service
// @Transactional
// public class UserServiceImpl implements UserService {

//     private final UserRepository userRepository;
//     private final BCryptPasswordEncoder passwordEncoder;
//     private final JwtUtil jwtUtil;

//     public UserServiceImpl(UserRepository userRepository,
//                            BCryptPasswordEncoder passwordEncoder,
//                            JwtUtil jwtUtil) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//         this.jwtUtil = jwtUtil;
//     }

//     // ✅ Register a new user
//     @Override
//     public User register(RegisterRequest request) {
//         if (userRepository.existsByEmail(request.getEmail())) {
//             throw new ValidationException("Email already registered");
//         }

//         User user = new User();
//         user.setFullName(request.getFullName());
//         user.setEmail(request.getEmail());
//         user.setPassword(passwordEncoder.encode(request.getPassword()));
//         user.setRole(request.getRole() != null ? request.getRole() : "LEARNER");
//         user.setPreferredLearningStyle(request.getPreferredLearningStyle());

//         return userRepository.save(user);
//     }

//     // ✅ Login user and return JWT token
//     @Override
//     public AuthResponse login(String email, String password) {
//         User user = userRepository.findByEmail(email)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));

//         if (!passwordEncoder.matches(password, user.getPassword())) {
//             throw new ValidationException("Invalid password");
//         }

//         Map<String, Object> claims = new HashMap<>();
//         claims.put("userId", user.getId());
//         claims.put("role", user.getRole());

//         String token = jwtUtil.generateToken(claims, user.getEmail());

//         return AuthResponse.builder()
//                 .accessToken(token)
//                 .userId(user.getId())
//                 .email(user.getEmail())
//                 .role(user.getRole())
//                 .build();
//     }

//     // ✅ Find user by ID
//     @Override
//     public User findById(Long id) {
//         return userRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//     }

//     // ✅ Find user by email
//     @Override
//     public User findByEmail(String email) {
//         return userRepository.findByEmail(email)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//     }

//     // ✅ Update user details
//     @Override
//     public User updateUser(Long id, User updatedUser) {
//         User existing = userRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));

//         if (updatedUser.getFullName() != null)
//             existing.setFullName(updatedUser.getFullName());
//         if (updatedUser.getEmail() != null)
//             existing.setEmail(updatedUser.getEmail());
//         if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty())
//             existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
//         if (updatedUser.getPreferredLearningStyle() != null)
//             existing.setPreferredLearningStyle(updatedUser.getPreferredLearningStyle());
//         if (updatedUser.getRole() != null)
//             existing.setRole(updatedUser.getRole());

//         return userRepository.save(existing);
//     }
// }
package com.example.demo.service.impl;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ✅ Register a new user
    @Override
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "LEARNER");
        user.setPreferredLearningStyle(request.getPreferredLearningStyle());

        return userRepository.save(user);
    }

    // ✅ Login user and return JWT token
    @Override
    public AuthResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ValidationException("Invalid password");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole());

        String token = jwtUtil.generateToken(claims, user.getEmail());

        return AuthResponse.builder()
                .accessToken(token)
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    // ✅ Find user by ID
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // ✅ Find user by email
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // ✅ Update existing user
    @Override
    public User updateUser(Long id, User updatedUser) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (updatedUser.getFullName() != null)
            existing.setFullName(updatedUser.getFullName());
        if (updatedUser.getEmail() != null)
            existing.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty())
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        if (updatedUser.getPreferredLearningStyle() != null)
            existing.setPreferredLearningStyle(updatedUser.getPreferredLearningStyle());
        if (updatedUser.getRole() != null)
            existing.setRole(updatedUser.getRole());

        return userRepository.save(existing);
    }
}
