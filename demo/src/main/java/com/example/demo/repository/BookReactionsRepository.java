package com.example.demo.repository;

import com.example.demo.model.BookReactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookReactionsRepository extends JpaRepository<BookReactions, Long> {

    Optional<BookReactions> findByUserUserIdAndBookBookId(Long userId, Long bookId);

    long countByBookBookIdAndLikedTrue(Long bookId);
}
