package cn.xdevops.application.services;

import cn.xdevops.domain.model.Book;
import cn.xdevops.infrastructure.jpa.entities.BookDetailJpaEntity;
import cn.xdevops.infrastructure.jpa.entities.BookJpaEntity;
import cn.xdevops.infrastructure.jpa.repositories.BookDetailRepository;
import cn.xdevops.infrastructure.jpa.repositories.BookRepository;
import cn.xdevops.interfaces.dto.CreateBookRequest;
import cn.xdevops.interfaces.transform.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BookService {

    private BookRepository bookRepository;
    private BookDetailRepository bookDetailRepository;

    public BookService(BookRepository bookRepository, BookDetailRepository bookDetailRepository) {
        this.bookRepository = bookRepository;
        this.bookDetailRepository = bookDetailRepository;
    }

    @Transactional
    public Book createBook(CreateBookRequest createBookRequest) {
        BookJpaEntity newBookJpaEntity = BookMapper.MAPPER.toBookJpaEntity(createBookRequest);
        newBookJpaEntity.setCreateTime(LocalDateTime.now());
        newBookJpaEntity.setUpdateTime(LocalDateTime.now());

        BookDetailJpaEntity newBookDetailJpaEntity = BookMapper.MAPPER.toBookDetailJpaEntity(createBookRequest);
        newBookDetailJpaEntity.setCreateTime(LocalDateTime.now());
        newBookDetailJpaEntity.setUpdateTime(LocalDateTime.now());

        BookJpaEntity savedBookJpaEntity = bookRepository.save(newBookJpaEntity);

        newBookDetailJpaEntity.setBookId(savedBookJpaEntity.getId());
        BookDetailJpaEntity savedBookDetailJpaEntity = bookDetailRepository.save(newBookDetailJpaEntity);

        return BookMapper.MAPPER.toBook(savedBookJpaEntity, savedBookDetailJpaEntity);
    }

    public Book findBookById(Long id) {
        return null; // TODO
    }
}
