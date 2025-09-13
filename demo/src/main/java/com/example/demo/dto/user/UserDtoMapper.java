package com.example.demo.dto.user;

import com.example.demo.model.User;

public final class UserDtoMapper {
    private UserDtoMapper() {}

    public static UserResponse toResponse(User u) {
        if (u == null) return null;
        return UserResponse.builder()
                .id(u.getUserId())
                .username(u.getUsername())
                .build();
    }

    public static User toEntity(UserSignupRequest r) {
        User u = new User();
        u.setUsername(r.getUsername());
        u.setPassword(r.getPassword());
        return u;
    }
}
