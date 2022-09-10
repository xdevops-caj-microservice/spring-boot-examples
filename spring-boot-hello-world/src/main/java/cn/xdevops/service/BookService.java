package cn.xdevops.service;

import cn.xdevops.entity.Book;
import cn.xdevops.exception.BookNotFoundException;
import cn.xdevops.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        log.info("Call BookService.save() ....");
        return bookRepository.save(book);
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book saveOrUpdateBook(Book newBook, Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setName(newBook.getName());
                    book.setAuthor(newBook.getAuthor());
                    book.setPrice(newBook.getPrice());
                    return bookRepository.save(book);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return bookRepository.save(newBook);
                });
    }

    public void deleteBookById(Long id) {
        log.info("Call BookService.deleteBookById() ....");
        bookRepository.deleteById(id);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
}
