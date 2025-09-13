package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    public Book() {
    }

    public Book(String title,String author,Integer publicationYear){
        this.title=title;
        this.author=author;
        this.publicationYear=publicationYear;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    private String author;

    @Min(value = 1000 , message = "Year must be a valid number")
    private Integer publicationYear;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + publicationYear +
                '}';
    }
}
