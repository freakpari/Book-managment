package com.example.demo.service;


import com.example.demo.model.Book;
import com.example.demo.model.BookReactions;
import com.example.demo.model.User;
import com.example.demo.repository.BookReactionsRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookReactionsService {

    private final BookReactionsRepository bookReactionsRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookReactionsService(
            BookReactionsRepository bookReactionsRepository,
            UserRepository userRepository,
            BookRepository bookRepository
    ) {
        this.bookReactionsRepository = bookReactionsRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void likeOrDislikeBook(Long bookId, Long userId, boolean like) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Optional<BookReactions> existingReaction = bookReactionsRepository.findByUserAndBook(user, book);

        if (existingReaction.isPresent()) {
            BookReactions bookReaction = existingReaction.get();
            if (bookReaction.isLiked() == like) {
                bookReactionsRepository.delete(bookReaction);
            } else {
                bookReaction.setLiked(like);
                bookReactionsRepository.save(bookReaction);
            }
        } else {
            BookReactions newReaction = new BookReactions();
            newReaction.setBook(book);
            newReaction.setUser(user);
            newReaction.setLiked(like);
            bookReactionsRepository.save(newReaction);
        }

    }
    public long countLikes(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return bookReactionsRepository.countByBookAndLikedTrue(book);
    }

}


