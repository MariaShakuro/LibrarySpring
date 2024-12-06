package controller;

import com.example.library.controller.ResponseRestController;
import com.example.library.entity.Book;
import com.example.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.Optional;
import static org.mockito.Mockito.*;

@WebMvcTest(ResponseRestController.class)
public class ResponseRestControllerTest {

    @Mock
    private BookService bookService;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        webTestClient.get().uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class);
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book(bookId, "1234567890", "Test Book", "Fiction", "Test Description", "Test Author");

        when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));

        webTestClient.get().uri("/api/books/{id}", bookId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .isEqualTo(book);
    }

    @Test
    public void testGetBookByIsbn() {
        String isbn = "1234567890";
        Book book = new Book(1L, isbn, "Test Book", "Fiction", "Test Description", "Test Author");

        when(bookService.getBookByIsbn(isbn)).thenReturn(Optional.of(book));

        webTestClient.get().uri("/api/books/isbn/{isbn}", isbn)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .isEqualTo(book);
    }

    @Test
    public void testAddBook() {
        Book book = new Book(1L, "1234567890", "Test Book", "Fiction", "Test Description", "Test Author");

        when(bookService.addBook(any(Book.class))).thenReturn(book);
        when(bookService.notifyBook(anyLong(), any(Book.class))).thenReturn(Mono.empty());

        webTestClient.post().uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .isEqualTo(book);
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        Book book = new Book(bookId, "1234567890", "Test Book", "Fiction", "Test Description", "Test Author");

        when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));
        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(book);

        webTestClient.patch().uri("/api/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .isEqualTo(book);
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;

        when(bookService.getBookById(bookId)).thenReturn(Optional.of(new Book()));
        doNothing().when(bookService).deleteBook(bookId);

        webTestClient.delete().uri("/api/books/{id}", bookId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
