package com.example.demo.dto.reaction;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class BookReactionRequest {
    @NotNull
    private Long userId;
}