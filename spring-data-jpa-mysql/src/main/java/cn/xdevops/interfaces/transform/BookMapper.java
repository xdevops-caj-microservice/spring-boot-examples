package cn.xdevops.interfaces.transform;

import cn.xdevops.domain.model.Book;
import cn.xdevops.infrastructure.jpa.entities.BookDetailJpaEntity;
import cn.xdevops.infrastructure.jpa.entities.BookJpaEntity;
import cn.xdevops.interfaces.dto.CreateBookRequest;
import cn.xdevops.interfaces.dto.CreateBookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper MAPPER = Mappers.getMapper(BookMapper.class);

    CreateBookResponse toCreateBookResponse(Book book);

    BookJpaEntity toBookJpaEntity(CreateBookRequest createBookRequest);

    BookDetailJpaEntity toBookDetailJpaEntity(CreateBookRequest createBookRequest);

    Book toBook(BookJpaEntity bookJpaEntity, BookDetailJpaEntity bookDetailJpaEntity);
}
