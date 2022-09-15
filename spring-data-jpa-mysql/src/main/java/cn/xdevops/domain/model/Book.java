package cn.xdevops.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String bookId;
    private String bookName;
    private String isbn;
    private String description;
    private BigDecimal price;
    private LocalDate publishDate;
    private Integer pageCount;
    private Integer wordCount;
    private String paperFormat;
    private String paperType;
    private String packageType;
}
