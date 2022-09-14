package cn.xdevops.transform;

import cn.xdevops.domain.model.entities.Book;
import cn.xdevops.jpaentities.BookJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper MAPPER = Mappers.getMapper(BookMapper.class);

    BookJpaEntity bookToJpaEntity(Book book);

    Book jpaEntityToBook(BookJpaEntity bookJpaEntity);

    List<Book> jpaEntityListToBookList(List<BookJpaEntity> bookJpaEntityList);
}
