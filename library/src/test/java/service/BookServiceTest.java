package service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.library.client.JwtAuthClient;
import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookProducer;
import com.example.library.service.BookService;
import com.example.library.service.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookProducer bookProducer;

    @Mock
    private JwtAuthClient jwtAuthClient;

    @InjectMocks
    private BookService bookService;

    private String validToken = "valid-jwt-token";
    private String invalidToken = "invalid-jwt-token";

    @BeforeEach
    void setUp() {
        when(jwtAuthClient.validateToken("Bearer " + validToken)).thenReturn(true);
        when(jwtAuthClient.validateToken("Bearer " + invalidToken)).thenReturn(false);
    }

    @Test
    void testGetAllBooks_ValidToken() throws UnauthorizedException {
        Book book1 = new Book();
        book1.setDeleted(false);
        Book book2 = new Book();
        book2.setDeleted(false);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks(validToken);

        assertNotNull(books);
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetAllBooks_InvalidToken() {
        assertThrows(UnauthorizedException.class, () -> {
            bookService.getAllBooks(invalidToken);
        });
    }

    @Test
    void testGetBookById_ValidToken() throws UnauthorizedException {
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L, validToken);

        assertTrue(foundBook.isPresent());
        assertEquals(1L, foundBook.get().getId());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_InvalidToken() {
        assertThrows(UnauthorizedException.class, () -> {
            bookService.getBookById(1L, invalidToken);
        });
    }

    @Test
    void testAddBook_ValidToken() throws UnauthorizedException {
        Book book = new Book();
        book.setIsbn("123");

        when(bookRepository.getBookByIsbn("123")).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.addBook(book, validToken);

        assertNotNull(createdBook);
        verify(bookRepository, times(1)).save(book);
        verify(bookProducer, times(1)).sendBookEvent(eq("new-book-topic"), any(Long.class));
    }

    @Test
    void testAddBook_InvalidToken() {
        Book book = new Book();
        book.setIsbn("123");

        assertThrows(UnauthorizedException.class, () -> {
            bookService.addBook(book, invalidToken);
        });
    }

    @Test
    void testDeleteBook_ValidToken() throws UnauthorizedException {
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L, validToken);

        assertTrue(book.isDeleted());
        verify(bookRepository, times(1)).save(book);
        verify(bookProducer, times(1)).sendBookEvent(eq("delete-book-topic"), eq(1L));
    }

    @Test
    void testDeleteBook_InvalidToken() {
        assertThrows(UnauthorizedException.class, () -> {
            bookService.deleteBook(1L, invalidToken);
        });
    }

    @Test
    void testUpdateBook_ValidToken() throws UnauthorizedException {
        Book book = new Book();
        book.setId(1L);
        Book updatedDetails = new Book();
        updatedDetails.setName("Updated Name");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedBook = bookService.updateBook(1L, updatedDetails, validToken);

        assertNotNull(updatedBook);
        assertEquals("Updated Name", updatedBook.getName());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBook_InvalidToken() {
        Book updatedDetails = new Book();

        assertThrows(UnauthorizedException.class, () -> {
            bookService.updateBook(1L, updatedDetails, invalidToken);
        });
    }
}

