package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("rawpassword");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("rawpassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.registerUser(user);

        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).encode("rawpassword");
        verify(userRepository).save(user);

        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("testuser", savedUser.getUsername());
    }

    @Test
    void registerUser_UsernameAlreadyTaken_ThrowsException() {
        User user = new User();
        user.setUsername("existingUser");
        user.setPassword("anyPassword");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("Username already taken", exception.getMessage());

        verify(userRepository).findByUsername("existingUser");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any());
    }

    @Test
    void findByUsername_ReturnsUser() {
        User user = new User();
        user.setUsername("someuser");

        when(userRepository.findByUsername("someuser")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("someuser");

        assertTrue(result.isPresent());
        assertEquals("someuser", result.get().getUsername());
    }
}
