package com.example.demo.service;


import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book){
        return bookRepository.save(book);
    }
    public List<Book> getAllBooks(){

        return bookRepository.findAll();
    }
    public Optional<Book> getBookById(Long id) {

        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByTitle(String title) {

        return bookRepository.findByTitle(title);
    }
    public boolean deleteBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("the book not found" + id);
        }
    }


    public Book updateBook(Long id,Book updateBook){
        Optional<Book> exitingBook = bookRepository.findById(id);
        if(exitingBook.isPresent()){
            Book book = exitingBook.get();
            book.setTitle(updateBook.getTitle());
            book.setAuthor(updateBook.getAuthor());
            book.setPublicationYear(updateBook.getPublicationYear());
            return bookRepository.save(book);
        }
        throw new RuntimeException("book not found"+ id);
    }
}
