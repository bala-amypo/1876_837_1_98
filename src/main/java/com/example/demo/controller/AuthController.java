// package com.example.demo.controller;

// import com.example.demo.dto.AuthRequest;
// import com.example.demo.dto.AuthResponse;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.model.User;
// import com.example.demo.service.UserService;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// @Tag(name = "Authentication", description = "User Registration, Login & Edit APIs")
// public class AuthController {

//     private final UserService userService;

//     public AuthController(UserService userService) {
//         this.userService = userService;
//     }

//     // ✅ Register endpoint using RegisterRequest DTO
//     @PostMapping("/register")
//     public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
//         User saved = userService.register(request);
//         return ResponseEntity.ok(saved);
//     }

//     // ✅ Login endpoint
//     @PostMapping("/login")
//     public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
//         AuthResponse response = userService.login(request.getEmail(), request.getPassword());
//         return ResponseEntity.ok(response);
//     }

//     // ✅ Update user endpoint
//     @PutMapping("/users/{id}")
//     public ResponseEntity<User> updateUser(
//             @PathVariable Long id,
//             @RequestBody User updatedUser) {
//         User user = userService.updateUser(id, updatedUser);
//         return ResponseEntity.ok(user);
//     }
// }
package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User Registration & Login APIs")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ✅ User Registration
    @Operation(summary = "Register a new user", description = "Registers a new user account with email, password, role, and learning style.")
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User saved = userService.register(request);
        return ResponseEntity.ok(saved);
    }

    // ✅ User Login
    @Operation(summary = "Login a user", description = "Authenticates a user and returns a JWT token.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }
}
