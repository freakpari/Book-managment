package com.example.demo.dto.book;

import com.example.demo.model.Book;

public final class BookDtoMapper {
    private BookDtoMapper() {}

    public static BookResponse toResponse(Book b) {
        return BookResponse.builder()
                .id(b.getBookId())
                .title(b.getTitle())
                .author(b.getAuthor())
                .publicationYear(b.getPublicationYear())
                .build();
    }

    public static Book toEntity(BookCreateRequest r) {
        Book b = new Book();
        b.setTitle(r.getTitle());
        b.setAuthor(r.getAuthor());
        b.setPublicationYear(r.getPublicationYear());
        return b;
    }

    public static void apply(BookUpdateRequest r, Book b) {
        b.setTitle(r.getTitle());
        b.setAuthor(r.getAuthor());
        b.setPublicationYear(r.getPublicationYear());
    }

    public static void applyPatch(BookPatchRequest r, Book b) {
        if (r.getTitle() != null)  b.setTitle(r.getTitle());
        if (r.getAuthor() != null) b.setAuthor(r.getAuthor());
        if (r.getPublicationYear() != null) b.setPublicationYear(r.getPublicationYear());
    }
}
