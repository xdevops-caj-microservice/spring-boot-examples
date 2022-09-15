package cn.xdevops.infrastructure.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_book_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "description")
    private String description;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "word_count")
    private Integer wordCount;

    @Column(name = "paper_format")
    private String paperFormat;

    @Column(name = "paper_type")
    private String paperType;

    @Column(name = "package_type")
    private String packageType;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

}
