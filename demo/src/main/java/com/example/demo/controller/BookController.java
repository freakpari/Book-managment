package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Book addBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }
    @GetMapping
    public List<Book> getAllBooks() {

        return bookService.getAllBooks();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title){
        return bookService.getBookByTitle(title)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
    @PatchMapping("/{id}")
    public Book updateBook(@PathVariable Long id,@Valid @RequestBody Book book){
        return bookService.updateBook(id,book);
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Book> deleteBookById(@PathVariable Long id){
        boohttp://localhost:8080/api/bookskService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
