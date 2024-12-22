package controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.example.library.entity.Book;
import com.example.library.service.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.library.LibraryApplication;
import com.example.library.controller.ResponseRestController;

import com.example.library.service.BookService;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = LibraryApplication.class)
public class ResponseRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private ResponseRestController responseRestController;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(responseRestController).build();
        book = new Book();
        book.setId(1L);
        book.setName("Test Book");
    }

    @Test
    void testGetBookById() throws Exception, UnauthorizedException {
        Long bookId = 1L;
        String token = "valid-token";

        when(bookService.getBookById(bookId, token)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/{id}", bookId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.name").value("Test Book"));

        verify(bookService, times(1)).getBookById(bookId, token);
    }
}




