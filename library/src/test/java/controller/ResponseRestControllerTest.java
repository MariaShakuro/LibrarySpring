package controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Collections;

import com.example.library.controller.ResponseRestController;
import com.example.library.entity.Book;
import com.example.library.service.BookService;
import com.example.library.service.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ResponseRestController.class)
@ExtendWith(SpringExtension.class)
public class ResponseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private BookService bookService;

    private String validToken = "valid-jwt-token";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void testGetAllBooks() throws Exception, UnauthorizedException {
        when(bookService.getAllBooks(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/books")
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void testGetBookById() throws Exception, UnauthorizedException {
        Book book = new Book();
        book.setId(1L);

        when(bookService.getBookById(eq(1L), anyString())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/{id}", 1L)
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void testGetBookByIsbn() throws Exception, UnauthorizedException {
        Book book = new Book();
        book.setIsbn("123");

        when(bookService.getBookByIsbn(eq("123"), anyString())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/isbn/{isbn}", "123")
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isbn").value("123"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testAddBook() throws Exception, UnauthorizedException {
        Book book = new Book();
        book.setIsbn("123");

        when(bookService.addBook(any(Book.class), anyString())).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\": \"123\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isbn").value("123"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testUpdateBook() throws Exception, UnauthorizedException {
        Book book = new Book();
        book.setId(1L);

        when(bookService.getBookById(eq(1L), anyString())).thenReturn(Optional.of(book));
        when(bookService.updateBook(eq(1L), any(Book.class), anyString())).thenReturn(book);

        mockMvc.perform(patch("/api/books/{id}", 1L)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Name\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testDeleteBook() throws Exception, UnauthorizedException {
        Book book = new Book();
        book.setId(1L);

        when(bookService.getBookById(eq(1L), anyString())).thenReturn(Optional.of(book));
        doNothing().when(bookService).deleteBook(eq(1L), anyString());

        mockMvc.perform(delete("/api/books/{id}", 1L)
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isNoContent());
    }
}

