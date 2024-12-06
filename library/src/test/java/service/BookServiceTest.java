package service;
import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setIsbn("1234567890");

        when(bookRepository.getBookByIsbn("1234567890")).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.addBook(book);

        assertNotNull(savedBook);
        assertEquals("1234567890", savedBook.getIsbn());
        verify(bookRepository, times(1)).getBookByIsbn("1234567890");
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testGetAllBooks() {
        bookService.getAllBooks();
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Book> retrievedBook = bookService.getBookById(bookId);

        assertTrue(retrievedBook.isPresent());
        assertEquals(bookId, retrievedBook.get().getId());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        Book updatedDetails = new Book();
        updatedDetails.setName("New Name");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book updatedBook = bookService.updateBook(bookId, updatedDetails);

        assertNotNull(updatedBook);
        assertEquals("New Name", updatedBook.getName());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;

        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }

}
