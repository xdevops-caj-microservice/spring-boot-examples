package cn.xdevops.controller;

import cn.xdevops.entity.Book;
import cn.xdevops.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Book newBook(@RequestBody Book book) {
        log.info("Call BookController.newBook() ...");
        return bookService.save(book);
    }

    @GetMapping("/{id}")
    public Book findBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    @PutMapping("/{id}")
    public Book saveOrUpdateBook(@RequestBody Book newBook, @PathVariable Long id) {
        return bookService.saveOrUpdateBook(newBook, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        log.info("Call BookController.deleteBook() ...");
        bookService.deleteBookById(id);
    }

    @GetMapping
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }
 }
