package com.example.demo.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "password")
public class UserLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
