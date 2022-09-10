package cn.xdevops.controller;

import cn.xdevops.entity.Book;
import cn.xdevops.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("should create a new book")
    void shouldCreateANewBook() throws Exception {
        String bookJson = "{\"name\": \"Spring Boot Hello World\", \"author\":\"William\", \"price\": 8.88}";

        mockMvc.perform(post("/books")
                .content(bookJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // ensure bookService.save(book) method is called
        // TODO but why no logs printed in BookService.save() method?
        Mockito.verify(bookService).save(Mockito.any(Book.class));
    }

    @Test
    @DisplayName("should find a book by id")
    void shouldFindABookById() throws Exception {
        Mockito.when(bookService.findBookById(1L))
                .thenReturn(new Book(1L, "Spring Boot Hello World", "William",  BigDecimal.valueOf(8.88)));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring Boot Hello World")))
                .andExpect(jsonPath("$.author", is("William")))
                .andExpect(jsonPath("$.price", is(8.88)));
    }

    @Test
    @DisplayName("should find all books")
    void shouldFindAllBooks() throws Exception {
        Mockito.when(bookService.findAllBooks())
                .thenReturn(
                        List.of(
                                new Book(1L, "Spring Boot Hello World", "William",  BigDecimal.valueOf(8.88)),
                                new Book(2L, "REST API design", "Martin",  BigDecimal.valueOf(9.99))
                                ));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Spring Boot Hello World")))
                .andExpect(jsonPath("$[0].author", is("William")))
                .andExpect(jsonPath("$[0].price", is(8.88)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("REST API design")))
                .andExpect(jsonPath("$[1].author", is("Martin")))
                .andExpect(jsonPath("$[1].price", is(9.99)));
    }

    @Test
    @DisplayName("should delete a book by id")
    void shouldDeleteABookById() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk());

        // ensure bookService.deleteBookById(id) method is called
        // TODO but why no logs printed in BookService.deleteBookById() method?
        Mockito.verify(bookService).deleteBookById(Mockito.anyLong());
    }
}
