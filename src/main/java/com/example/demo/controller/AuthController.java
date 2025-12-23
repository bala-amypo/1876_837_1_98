// package com.example.demo.controller;

// import com.example.demo.dto.AuthRequest;
// import com.example.demo.dto.AuthResponse;
// import com.example.demo.model.User;
// import com.example.demo.service.UserService;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// @Tag(name = "Authentication", description = "User Registration & Login APIs")
// public class AuthController {

//     private final UserService userService;

//     public AuthController(UserService userService) {
//         this.userService = userService;
//     }

 
//     @PostMapping("/register")
//     public ResponseEntity<User> registerUser(@RequestBody User user) {
//         User createdUser = userService.registerUser(user);
//         return ResponseEntity.ok(createdUser);
//     }

//     @PostMapping("/login")
//     public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request) {
//         AuthResponse response = userService.loginUser(request);
//         return ResponseEntity.ok(response);
//     }
// }
package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user and get JWT token")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse response = userService.login(authRequest.getEmail(), authRequest.getPassword());
        return ResponseEntity.ok(response);
    }
}