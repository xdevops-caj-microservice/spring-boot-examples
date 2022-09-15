package cn.xdevops.infrastructure.jpa.repositories;

import cn.xdevops.domain.model.Publisher;
import cn.xdevops.infrastructure.jpa.entities.PublisherJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<PublisherJpaEntity, Long> {

    @Query("SELECT p FROM PublisherJpaEntity p WHERE p.deleted = false AND p.id = ?1")
    Optional<PublisherJpaEntity> findPublisherById(Long id);

    /**
     * `findByIdAndDeleted(id, false)` same as `findPublisherById(id)`
     * @param id
     * @param deleted
     * @return
     */
    Optional<PublisherJpaEntity> findByIdAndDeleted(Long id, boolean deleted);

    /**
     * `findByIdAndDeletedFalse(id) same as findByIdAndDeleted(id, false)
     * @param id
     * @return
     */
    Optional<PublisherJpaEntity> findByIdAndDeletedFalse(Long id);



    @Query(
            value = "SELECT p.* FROM t_publisher p WHERE p.is_deleted = 0 ORDER BY p.publisher_name ASC",
            nativeQuery = true
    )
    List<PublisherJpaEntity> findAllPublishers();

    /**
     * `findByDeletedOrderByPublisherNameAsc(false)` same as `findAllPublishers()`
     * @param deleted
     * @return
     */
    List<PublisherJpaEntity> findByDeletedOrderByPublisherNameAsc(boolean deleted);

    /**
     * `findByDeletedFalseOrderByPublisherNameAsc()` same as `findByDeletedOrderByPublisherNameAsc(false)`
     * @return
     */
    List<PublisherJpaEntity> findByDeletedFalseOrderByPublisherNameAsc();


    /**
     * Use MySQL `now()` function is not recommended!
     * @param id
     */
    @Modifying
    @Query(
            value = "UPDATE t_publisher p SET p.is_deleted = 1, p.update_time = now() WHERE p.id = ?1",
            nativeQuery = true
    )
    @Transactional
    void deletePublisherById(Long id);

    /**
     * `updateDeletedAndUpdateTimeById(false, updateTime, id)` same as `deletePublisherById(id)`
     * use named parameters `@Param`
     * @param deleted
     * @param updateTime
     * @param id
     */
    @Modifying
    @Query(value = "UPDATE PublisherJpaEntity p SET p.deleted = :deleted, p.updateTime = :updateTime WHERE p.id = :id")
    @Transactional
    void updateDeletedAndUpdateTimeById(@Param("deleted") boolean deleted,
                                        @Param("updateTime") LocalDateTime updateTime,
                                        @Param("id") Long id);

    List<PublisherJpaEntity> findByCity(String city);

    List<PublisherJpaEntity> findByCityLike(String city);

    List<PublisherJpaEntity> findByCityContaining(String city);

    List<PublisherJpaEntity> findByCityIn(List<String> cityList);

    List<PublisherJpaEntity> findByOnboardDateGreaterThan(LocalDate onboardDate);

    List<PublisherJpaEntity> findByOnboardDateLessThan(LocalDate onboardDate);

    List<PublisherJpaEntity> findByOnboardDateBetween(LocalDate startDate, LocalDate endDate);
}
