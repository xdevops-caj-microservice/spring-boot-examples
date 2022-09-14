package cn.xdevops.domain.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private Long id;
    private String bookName;
    private String isbn;
    private String description;
    private BigDecimal price;
    private LocalDate publishDate;
}
