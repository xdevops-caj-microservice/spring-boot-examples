package cn.xdevops.application.services;

import cn.xdevops.interfaces.dto.CreateBookRequest;
import cn.xdevops.interfaces.dto.CreateBookResponse;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public CreateBookResponse createBook(CreateBookRequest createBookRequest) {
        Book book = new Book(createBookRequest);
        bookRepository.save(BookMapper.MAPPER.toJpaEntity(book));
        // TODO
        return null;
    }
}
