// package com.example.demo.exception;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// import java.util.HashMap;
// import java.util.Map;

// @RestControllerAdvice
// public class GlobalExceptionHandler {

//     @ExceptionHandler(ResourceNotFoundException.class)
//     public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
//         Map<String, Object> body = new HashMap<>();
//         body.put("success", false);
//         body.put("message", ex.getMessage());
//         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
//     }

//     @ExceptionHandler(ValidationException.class)
//     public ResponseEntity<Map<String, Object>> handleValidation(ValidationException ex) {
//         Map<String, Object> body = new HashMap<>();
//         body.put("success", false);
//         body.put("message", ex.getMessage());
//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
//     }

//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
//         Map<String, Object> body = new HashMap<>();
//         body.put("success", false);
//         body.put("message", ex.getMessage());
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
//     }
// }
package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}