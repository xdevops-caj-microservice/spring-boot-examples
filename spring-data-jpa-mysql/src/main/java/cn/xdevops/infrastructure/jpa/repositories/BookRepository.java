package cn.xdevops.infrastructure.jpa.repositories;

import cn.xdevops.infrastructure.jpa.entities.BookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<BookJpaEntity, Long> {

    @Query(
            value = "SELECT b.*, d.* FROM t_book b, t_book_detail d WHERE b.id = d.book_id AND b.id =?1",
            nativeQuery = true
    )
    void findBookById(Long id);
}
