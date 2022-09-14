package cn.xdevops.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookResponse {
    private String bookId;
    private String bookName;
    private String isbn;
    private String description;
    private BigDecimal price;
    private LocalDate publishDate;
    private List<Author> authors;
    private List<Publisher> publishers;

    private class Author {
        private String authorId;
        private String firstName;
        private String lastName;
        private String nationality;
    }

    private class Publisher {
        private String publisherId;
        private String publisherName;
    }
}