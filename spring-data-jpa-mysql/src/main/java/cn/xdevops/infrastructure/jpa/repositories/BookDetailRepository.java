package cn.xdevops.infrastructure.jpa.repositories;

import cn.xdevops.infrastructure.jpa.entities.BookDetailJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailRepository extends JpaRepository<BookDetailJpaEntity, Long> {
}
