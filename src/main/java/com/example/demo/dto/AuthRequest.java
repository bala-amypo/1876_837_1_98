// package com.example.demo.dto;
// import lombok.Data;

// @Data
// public class AuthRequest {
//     private String email;
//     private String password;
// }
package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;
}