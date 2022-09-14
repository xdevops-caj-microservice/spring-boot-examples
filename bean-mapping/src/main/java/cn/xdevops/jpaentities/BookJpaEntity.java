package cn.xdevops.jpaentities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "t_book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;
}
