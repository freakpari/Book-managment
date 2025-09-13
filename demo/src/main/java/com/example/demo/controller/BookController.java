package com.example.demo.controller;

import com.example.demo.dto.book.BookCreateRequest;
import com.example.demo.dto.book.BookPatchRequest;
import com.example.demo.dto.book.BookResponse;
import com.example.demo.dto.book.BookUpdateRequest;
import com.example.demo.dto.book.BookDtoMapper;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookCreateRequest req) {
        Book entity = BookDtoMapper.toEntity(req);
        Book saved = bookService.addBook(entity);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getBookId()).toUri();
        return ResponseEntity.created(location).body(BookDtoMapper.toResponse(saved));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookResponse> getAll() {
        return bookService.getAllBooks()
                .stream().map(BookDtoMapper::toResponse).toList();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(BookDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> getByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title)
                .map(BookDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody BookUpdateRequest req) {
        return bookService.getBookById(id)
                .map(existing -> {
                    BookDtoMapper.apply(req, existing);
                    Book saved = bookService.updateBook(id, existing);
                    return ResponseEntity.ok(BookDtoMapper.toResponse(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> patch(@PathVariable Long id,
                                              @RequestBody BookPatchRequest req) {
        return bookService.getBookById(id)
                .map(existing -> {
                    BookDtoMapper.applyPatch(req, existing);
                    Book saved = bookService.updateBook(id, existing);
                    return ResponseEntity.ok(BookDtoMapper.toResponse(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = bookService.deleteBookById(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
