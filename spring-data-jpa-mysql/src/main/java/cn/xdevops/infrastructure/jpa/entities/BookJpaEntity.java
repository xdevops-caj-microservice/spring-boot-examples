package cn.xdevops.infrastructure.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

}
