package com.example.demo.controller;

import com.example.demo.dto.reaction.BookReactionRequest;
import com.example.demo.dto.reaction.BookReactionDtoMapper;
import com.example.demo.dto.reaction.LikeCountResponse;
import com.example.demo.service.BookReactionsService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookReactionsController {

    private final BookReactionsService bookLikeService;

    public BookReactionsController(BookReactionsService bookLikeService) {
        this.bookLikeService = bookLikeService;
    }

    @PostMapping("/{bookId}/like")
    public ResponseEntity<?> likeBook(@PathVariable Long bookId,
                                      @Valid @RequestBody BookReactionRequest req) {
        return bookLikeService.likeOrDislikeBook(bookId, req.getUserId(), true)
                .map(saved -> ResponseEntity.ok(BookReactionDtoMapper.toResponse(saved)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping("/{bookId}/dislike")
    public ResponseEntity<?> dislikeBook(@PathVariable Long bookId,
                                         @Valid @RequestBody BookReactionRequest req) {
        return bookLikeService.likeOrDislikeBook(bookId, req.getUserId(), false)
                .map(saved -> ResponseEntity.ok(BookReactionDtoMapper.toResponse(saved)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(value = "/{bookId}/likes/count",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LikeCountResponse> getLikeCount(@PathVariable Long bookId) {
        long count = bookLikeService.countLikes(bookId);
        return ResponseEntity.ok(new LikeCountResponse(count));
    }
}
