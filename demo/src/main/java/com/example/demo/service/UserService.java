package com.example.demo.service;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
  public class UserService {

  @Autowired
  private  UserRepository userRepository;
  private    PasswordEncoder passwordEncoder;
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
    this.userRepository=userRepository;
    this.passwordEncoder=passwordEncoder;
  }
   public User registerUser(User user) {

        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }



}
