package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.BookReactions;
import com.example.demo.model.User;
import com.example.demo.repository.BookReactionsRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookReactionsService {

    private final BookReactionsRepository reactionsRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    @Transactional
    public Optional<BookReactions> likeOrDislikeBook(Long bookId, Long userId, boolean like) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + bookId));

        Optional<BookReactions> existing = reactionsRepo.findByUserUserIdAndBookBookId(userId, bookId);

        if (existing.isPresent()) {
            BookReactions r = existing.get();
            if (r.isLiked() == like) {
                reactionsRepo.delete(r);
                return Optional.empty();
            } else {
                r.setLiked(like);
                return Optional.of(reactionsRepo.save(r));
            }
        } else {
            BookReactions r = new BookReactions();
            r.setBook(book);
            r.setUser(user);
            r.setLiked(like);
            return Optional.of(reactionsRepo.save(r));
        }
    }

    @Transactional(readOnly = true)
    public long countLikes(Long bookId) {
        if (!bookRepo.existsById(bookId)) {
            throw new EntityNotFoundException("Book not found: " + bookId);
        }
        return reactionsRepo.countByBookBookIdAndLikedTrue(bookId);
    }
}
