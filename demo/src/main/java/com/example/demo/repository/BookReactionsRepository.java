package com.example.demo.repository;


import com.example.demo.model.Book;
import com.example.demo.model.BookReactions;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookReactionsRepository extends JpaRepository<BookReactions, Long> {
        Optional<BookReactions> findByUserAndBook(User user, Book book);
        long countByBookAndLikedTrue(Book book);

}
