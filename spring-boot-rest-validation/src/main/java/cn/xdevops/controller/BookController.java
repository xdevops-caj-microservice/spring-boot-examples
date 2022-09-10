package cn.xdevops.controller;

import cn.xdevops.entity.Book;
import cn.xdevops.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Book newBook(@RequestBody @Valid Book book) {
        return bookService.save(book);
    }

    @GetMapping("/{id}")
    public Book findBookById(@PathVariable @Min(1) Long id) {
        return bookService.findBookById(id);
    }

    @PutMapping("/{id}")
    public Book saveOrUpdateBook(@RequestBody Book newBook, @PathVariable @Min(1) Long id) {
        return bookService.saveOrUpdateBook(newBook, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable @Min(1) Long id) {
        bookService.deleteBookById(id);
    }

    @GetMapping
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }
 }
