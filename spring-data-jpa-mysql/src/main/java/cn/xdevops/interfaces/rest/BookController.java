package cn.xdevops.interfaces.rest;

import cn.xdevops.application.services.BookService;
import cn.xdevops.domain.model.Book;
import cn.xdevops.interfaces.dto.CreateBookRequest;
import cn.xdevops.interfaces.dto.CreateBookResponse;
import cn.xdevops.interfaces.transform.BookMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody CreateBookRequest createBookRequest) {
        return bookService.createBook(createBookRequest);
    }

    @GetMapping("/{id}")
    public Book findBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }
}
