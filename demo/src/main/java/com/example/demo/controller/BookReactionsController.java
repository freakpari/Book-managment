package com.example.demo.controller;


import com.example.demo.service.BookReactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookReactionsController {

    private final BookReactionsService bookLikeService;

    public BookReactionsController(BookReactionsService bookLikeService) {
        this.bookLikeService = bookLikeService;
    }

    @PostMapping("/{bookId}/like")
    public ResponseEntity<?> likeBook(@PathVariable Long bookId, @RequestParam Long userId) {
        bookLikeService.likeOrDislikeBook(bookId, userId, true);
        return ResponseEntity.ok("Book liked");
    }

    @PostMapping("/{bookId}/dislike")
    public ResponseEntity<?> dislikeBook(@PathVariable @RequestParam Long bookId, @RequestParam Long userId) {
        bookLikeService.likeOrDislikeBook(bookId, userId, false);
        return ResponseEntity.ok("Book disliked");
    }

    @GetMapping("/{bookId}/likes/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable @RequestParam Long bookId) {
        long count = bookLikeService.countLikes(bookId);
        return ResponseEntity.ok(count);
    }
}
