package cn.xdevops.infrastructure.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_publisher")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "publisher_name", nullable = false)
    private String publisherName;

    @Column(name = "city")
    private String city;

    @Column(name = "onboard_date")
    private LocalDate onboardDate;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;
}
