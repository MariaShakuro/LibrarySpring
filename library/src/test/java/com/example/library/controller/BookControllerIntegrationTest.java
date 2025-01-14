package com.example.library.controller;
/*
import com.example.library.BaseTest;
import com.example.library.dto.BookDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Integration Tests for BookController")
public class BookControllerIntegrationTest extends BaseTest {

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    @DisplayName("Should Get All Books")
    void shouldGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    @DisplayName("Should Get Book By ID")
    void shouldGetBookById() throws Exception {
        Long bookId = 1L;
        mockMvc.perform(get("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    @DisplayName("Should Get Book By ISBN")
    void shouldGetBookByIsbn() throws Exception {
        String isbn = "1234567890123";
        mockMvc.perform(get("/api/books/isbn/{isbn}", isbn)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(isbn));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should Add Book")
    void shouldAddBook() throws Exception {
        BookDto bookDto = BookDto.builder()
                .name("New Book")
                .isbn("1234567890123")
                .genre("Fiction")
                .description("A great book")
                .author("Author Name")
                .build();

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Book"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should Update Book")
    void shouldUpdateBook() throws Exception {
        Long bookId = 1L;
        BookDto bookDto = BookDto.builder()
                .name("Updated Book")
                .isbn("1234567890123")
                .genre("Updated Genre")
                .description("Updated Description")
                .author("Updated Author")
                .build();

        mockMvc.perform(patch("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Book"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should Delete Book")
    void shouldDeleteBook() throws Exception {
        Long bookId = 1L;

        mockMvc.perform(delete("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
*/