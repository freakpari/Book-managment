 package com.example.demo.dto.reaction;

import com.example.demo.model.BookReactions;

public final class BookReactionDtoMapper {
    private BookReactionDtoMapper() {}

    public static BookReactionResponse toResponse(BookReactions r) {
        if (r == null) return null;
        return BookReactionResponse.builder()
                .id(r.getId())
                .userId(r.getUser().getUserId())
                .bookId(r.getBook().getBookId())
                .liked(r.isLiked())
                .build();
    }
}

