package cn.xdevops.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookResponse {
    private Long bookId;
    private String bookName;
    private String isbn;
    private BigDecimal price;
    private LocalDate publishDate;
    private String description;
    private Integer pageCount;
    private Integer wordCount;
    private String paperFormat;
    private String paperType;
    private String packageType;
}