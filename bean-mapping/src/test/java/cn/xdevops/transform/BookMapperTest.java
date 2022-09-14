package cn.xdevops.transform;

import cn.xdevops.domain.model.entities.Book;
import cn.xdevops.jpaentities.BookJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Basic Mapping and Mapping List
 */
class BookMapperTest {

    @Test
    @DisplayName("should transform book to book jpa entity")
    void shouldTransformBookToBookJpaEntity() {
        Book book = Book.builder()
                .bookName("ABC")
                .description("level 1")
                .isbn("1001")
                .publishDate(LocalDate.of(2020, 12, 3))
                .build();

        BookJpaEntity bookJpaEntity = BookMapper.MAPPER.bookToJpaEntity(book);

        assertThat(bookJpaEntity).isNotNull();
        assertThat(bookJpaEntity.getBookName()).isEqualTo(book.getBookName());
        assertThat(bookJpaEntity.getDescription()).isEqualTo(book.getDescription());
        assertThat(bookJpaEntity.getIsbn()).isEqualTo(book.getIsbn());
        assertThat(bookJpaEntity.getPublishDate()).isEqualTo(book.getPublishDate());
    }

    @Test
    @DisplayName("should transform book jpa entity to book")
    void shouldTransformBookJpaEntityToBook() {
        BookJpaEntity bookJpaEntity = BookJpaEntity.builder()
                .bookName("ABC")
                .description("level 1")
                .isbn("1001")
                .publishDate(LocalDate.of(2020, 12, 3))
                .build();

        Book book = BookMapper.MAPPER.jpaEntityToBook(bookJpaEntity);

        assertThat(book).isNotNull();
        assertThat(book.getBookName()).isEqualTo(bookJpaEntity.getBookName());
        assertThat(book.getDescription()).isEqualTo(bookJpaEntity.getDescription());
        assertThat(book.getIsbn()).isEqualTo(bookJpaEntity.getIsbn());
        assertThat(book.getPublishDate()).isEqualTo(bookJpaEntity.getPublishDate());
    }

    @Test
    @DisplayName("should transform jpa entity list to book list")
    void shouldTransformJpaEntityListToBookList() {
        BookJpaEntity bookJpaEntity1 = BookJpaEntity.builder()
                .bookName("ABC")
                .description("level 1")
                .isbn("1001")
                .publishDate(LocalDate.of(2020, 12, 3))
                .build();

        BookJpaEntity bookJpaEntity2 = BookJpaEntity.builder()
                .bookName("Math")
                .description("exercise 1")
                .isbn("2002")
                .publishDate(LocalDate.of(2021, 1, 5))
                .build();

        List<BookJpaEntity> bookJpaEntityList = List.of(bookJpaEntity1, bookJpaEntity2);
        List<Book> bookList = BookMapper.MAPPER.jpaEntityListToBookList(bookJpaEntityList);

        assertThat(bookList).isNotNull().hasSize(bookJpaEntityList.size());
        assertThat(bookList).containsExactlyElementsOf(
                List.of(BookMapper.MAPPER.jpaEntityToBook(bookJpaEntity1),
                        BookMapper.MAPPER.jpaEntityToBook(bookJpaEntity2)));
    }

    @Test
    @DisplayName("should update existing jpa entity from book")
    void shouldUpdateExistingJpaEntityFromBook() {
        BookJpaEntity bookJpaEntity = BookJpaEntity.builder()
                .bookName("ABC")
                .description("level 1")
                .isbn("1001")
                .publishDate(LocalDate.of(2020, 12, 3))
                .build();

        Book book = Book.builder()
                .bookName("ABC English")
                .build();

        BookMapper.MAPPER.updateJpaEntityFromBook(book, bookJpaEntity);

        assertThat(bookJpaEntity).isNotNull();
        assertThat(bookJpaEntity.getBookName()).isEqualTo(book.getBookName());
        assertThat(bookJpaEntity.getDescription()).isNull();
        assertThat(bookJpaEntity.getIsbn()).isNull();
        assertThat(bookJpaEntity.getPublishDate()).isNull();
    }
}