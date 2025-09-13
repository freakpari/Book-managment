//package com.example.demo.controller;
//
//import com.example.demo.model.User;
//import com.example.demo.security.JwtUtil;
//import com.example.demo.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import java.util.Optional;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//public class AuthControllerTest {
//
//    private AuthController authController;
//    private UserService userService;
//    private JwtUtil jwtUtil;
//    private PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    void setUp() {
//        userService = mock(UserService.class);
//        jwtUtil = mock(JwtUtil.class);
//        authController = new AuthController(userService, jwtUtil);
//        passwordEncoder = new BCryptPasswordEncoder();
//        when(userService.passwordEncoder()).thenReturn(passwordEncoder);
//    }
//    @Test
//    void testSignupSuccess() {
//        User user = new User();
//        user.setUsername("test");
//        user.setPassword("1234");
//        when(userService.registerUser(user)).thenReturn(user);
//        ResponseEntity<?> response = authController.signup(user);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user, response.getBody());
//    }
//
//    @Test
//    void testSignupFail() {
//        User user = new User();
//        user.setUsername("test");
//        when(userService.registerUser(user)).thenThrow(new RuntimeException("Username exists"));
//        ResponseEntity<?> response = authController.signup(user);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Username exists", response.getBody());
//    }
//
//    @Test
//    public void testLoginSuccess() {
//        User inputUser = new User();
//        inputUser.setUsername("testuser");
//        inputUser.setPassword("correctpassword");
//        User savedUser = new User();
//        savedUser.setUsername("testuser");
//        savedUser.setPassword(passwordEncoder.encode("correctpassword"));
//        when(userService.findByUsername("testuser")).thenReturn(Optional.of(savedUser));
//        when(jwtUtil.generateToken("testuser")).thenReturn("fake-jwt-token");
//        ResponseEntity<?> response = authController.login(inputUser);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("fake-jwt-token", response.getBody());
//    }
//
//
//    @Test
//    void testLoginInvalidCredentials() {
//        User user = new User();
//        user.setUsername("test");
//        user.setPassword("wrongpass");
//        User existingUser = new User();
//        existingUser.setUsername("test");
//        String encodedPass = passwordEncoder.encode("correctpass");
//        existingUser.setPassword(encodedPass);
//        when(userService.findByUsername("test")).thenReturn(Optional.of(existingUser));
//        when(userService.passwordEncoder()).thenReturn(passwordEncoder);
//        assertFalse(passwordEncoder.matches("wrongpass", encodedPass));
//        ResponseEntity<?> response = authController.login(user);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Invalid username or password", response.getBody());
//    }
//
//
//    @Test
//    void testLoginUserNotFound() {
//        User user = new User();
//        user.setUsername("nonexistent");
//        user.setPassword("pass");
//        when(userService.findByUsername("nonexistent")).thenReturn(Optional.empty());
//        ResponseEntity<?> response = authController.login(user);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Invalid username or password", response.getBody());
//    }
//}
