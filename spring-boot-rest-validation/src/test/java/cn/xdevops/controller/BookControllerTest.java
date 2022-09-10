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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("should validating failure when missed book properties ")
    void shouldValidatingFailureWhenMissedBookProperties() throws Exception {
        String bookJson = "{\"name\": \"Microservices Patterns\"}";

        mockMvc.perform(post("/books")
                .content(bookJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors", hasItem("Author is mandatory")))
                .andExpect(jsonPath("$.errors", hasItem("Price is mandatory")));

        // ensure bookService.save(book) method is not called
        Mockito.verify(bookService, Mockito.times(0))
                .save(Mockito.any(Book.class));
    }

    @Test
    @DisplayName("should validating failure when find book by an invalid book id")
    void shouldValidatingFailureWhenFindBookByAnInvalidBookId() throws Exception{
        mockMvc.perform(get("/books/0"))
                .andExpect(status().isBadRequest());

        // TODO why no response body ?

        // ensure bookService.findBookById(id) method is not called
        Mockito.verify(bookService, Mockito.times(0))
                .findBookById(Mockito.anyLong());

    }
}
