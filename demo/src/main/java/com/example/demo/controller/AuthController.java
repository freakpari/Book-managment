package com.example.demo.controller;

import com.example.demo.dto.user.UserLoginRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.dto.user.UserSignupRequest;
import com.example.demo.model.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody UserSignupRequest req) {
        try {
            User u = new User();
            u.setUsername(req.getUsername());
            u.setPassword(req.getPassword());
            User saved = userService.registerUser(u);
            UserResponse body = UserResponse.builder()
                    .id(saved.getUserId())
                    .username(saved.getUsername())
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody UserLoginRequest req) {
        return userService.findByUsername(req.getUsername())
                .filter(u -> passwordEncoder.matches(req.getPassword(), u.getPassword()))
                .map(u -> {
                    String token = jwtUtil.generateToken(u.getUsername());
                    return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    public static class TokenResponse {
        private String token;
        private String tokenType;

        public TokenResponse() { }
        public TokenResponse(String token, String tokenType) {
            this.token = token;
            this.tokenType = tokenType;
        }
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getTokenType() { return tokenType; }
        public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    }
}
