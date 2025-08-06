package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        sampleBook = new Book("Sample Title", "Sample Author", 2020);
        sampleBook.setId(1L);
    }

    @Test
    void testAddBook() {
        when(bookRepository.save(sampleBook)).thenReturn(sampleBook);
        Book savedBook = bookService.addBook(sampleBook);

        assertNotNull(savedBook);
        assertEquals("Sample Title", savedBook.getTitle());
        verify(bookRepository, times(1)).save(sampleBook);
    }

    @Test
    void testGetAllBooks() {
        Book book2 = new Book("Second", "Author2", 2001);
        List<Book> bookList = Arrays.asList(sampleBook, book2);
        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> result = bookService.getAllBooks();
        assertEquals(2, result.size());
        verify(bookRepository).findAll();
    }

    @Test
    void testGetBookById_Found() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));

        Optional<Book> result = bookService.getBookById(1L);
        assertTrue(result.isPresent());
        assertEquals("Sample Title", result.get().getTitle());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.getBookById(2L);
        assertFalse(result.isPresent());
    }

    @Test
    void testGetBookByTitle_Found() {
        when(bookRepository.findByTitle("Sample Title")).thenReturn(Optional.of(sampleBook));

        Optional<Book> result = bookService.getBookByTitle("Sample Title");
        assertTrue(result.isPresent());
        assertEquals("Sample Author", result.get().getAuthor());
    }
    @Test
    void testDeleteBook_Success() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        boolean deleted = bookService.deleteBookById(id);
        assertTrue(deleted);
        verify(bookRepository).deleteById(id);
    }


    @Test
    void testDeleteBook_NotFound() {
        Long id = 1L;
        lenient().when(bookRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.deleteBookById(id);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }


    @Test
    void testUpdateBook_Success() {
        Book updated = new Book("New Title", "New Author", 2021);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.updateBook(1L, updated);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Author", result.getAuthor());
        assertEquals(2021, result.getPublicationYear());
    }

    @Test
    void testUpdateBook_NotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.updateBook(99L, sampleBook);
        });

        assertEquals("book not found99", exception.getMessage());
    }
}
