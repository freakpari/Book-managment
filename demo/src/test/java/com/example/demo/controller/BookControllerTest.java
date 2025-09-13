package com.example.demo.controller;

import com.example.demo.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void addBook() throws Exception {

        Book book = new Book();
        book.setAuthor("v");
        book.setTitle("b");
        book.setPublicationYear(2004);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    void getBookById() throws Exception{
        Book book = new Book();
        book.setAuthor("John");
        book.setTitle("Test Book");
        book.setPublicationYear(2000);

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Book savedBook = mapper.readValue(response, Book.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/" + savedBook.getBookId()))
                .andExpect(status().isOk());
    }


    @Test
    void getBookByTitle() throws Exception {
        Book book = new Book();
        book.setAuthor("Jane");
        book.setTitle("Unique Title");
        book.setPublicationYear(2015);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/title/Unique Title"))
                .andExpect(status().isOk());
    }


    @Test
    void updateBook() throws Exception {

        Book book = new Book();
        book.setAuthor("Old Author");
        book.setTitle("Update Me");
        book.setPublicationYear(1999);

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Book savedBook = mapper.readValue(response, Book.class);

        savedBook.setAuthor("New Author");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/books/" + savedBook.getBookId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(savedBook)))
                .andExpect(status().isOk());
    }


    @Test
    void deleteBookById() throws Exception {
        Book book = new Book();
        book.setAuthor("To Delete");
        book.setTitle("Delete Book");
        book.setPublicationYear(2022);

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Book savedBook = mapper.readValue(response, Book.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/" + savedBook.getBookId()))
                .andExpect(status().isNoContent());
    }

}