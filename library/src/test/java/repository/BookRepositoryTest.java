package repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setIsbn("123-456-789");
    }

    @Test
    void testGetBookByIsbn() {
        when(bookRepository.getBookByIsbn("123-456-789")).thenReturn(Optional.of(book));
        Optional<Book> foundBook = bookRepository.getBookByIsbn("123-456-789");


        assertTrue(foundBook.isPresent());
        assertEquals(book, foundBook.get());
    }
}
