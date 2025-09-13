package com.example.demo.dto.book;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class BookPatchRequest {
    private String title;
    private String author;

    @Min(value = 1000, message = "Year must be a valid number")
    private Integer publicationYear;
}
