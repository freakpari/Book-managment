package com.example.demo.dto.reaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder @ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookReactionResponse {
    private Long id;
    private Long userId;
    private Long bookId;
    private boolean liked;
}
