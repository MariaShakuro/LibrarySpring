package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.BookInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
@WebMvcTest(NotifyController.class)
public class NotifyControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private BookInfoService bookInfoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReceiveBookInfo() {
        Book book = new Book(1L, "1234567890", "Test Book", "Fiction", "Test Description", "Test Author");

        doNothing().when(bookInfoService).saveBookInfo(anyLong());

        webTestClient.post().uri("/api/freebooks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                .expectStatus().isOk();
    }
}
