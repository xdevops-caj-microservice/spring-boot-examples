package cn.xdevops.infrastructure.jpa.repositories;

import cn.xdevops.infrastructure.jpa.entities.PublisherJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<PublisherJpaEntity, Long> {

    @Query("SELECT p FROM PublisherJpaEntity p WHERE p.deleted = false AND p.id = ?1")
    Optional<PublisherJpaEntity> findPublisherById(Long id);

    @Query(
            value = "SELECT p.* FROM t_publisher p WHERE p.is_deleted = 0 ORDER BY p.publisher_name ASC",
            nativeQuery = true
    )
    List<PublisherJpaEntity> findAllPublishers();

    @Modifying
    @Query("UPDATE PublisherJpaEntity p SET p.deleted = true WHERE p.id = ?1")
    @Transactional
    void deletePublisherById(Long id);
}
