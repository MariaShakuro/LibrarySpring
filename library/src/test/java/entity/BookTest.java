package entity;

import com.example.library.entity.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void testNoArgsConstructor() {
        Book book = new Book();
        assertNotNull(book);
    }

    @Test
    public void testAllArgsConstructor() {
        Book book = new Book(1L, "1234567890", "Test Book", "Fiction", "Test Description", "Test Author");
        assertEquals(1L, book.getId());
        assertEquals("1234567890", book.getIsbn());
        assertEquals("Test Book", book.getName());
        assertEquals("Fiction", book.getGenre());
        assertEquals("Test Description", book.getDescription());
        assertEquals("Test Author", book.getAuthor());
    }

    @Test
    public void testGettersAndSetters() {
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("1234567890");
        book.setName("Test Book");
        book.setGenre("Fiction");
        book.setDescription("Test Description");
        book.setAuthor("Test Author");

        assertEquals(1L, book.getId());
        assertEquals("1234567890", book.getIsbn());
        assertEquals("Test Book", book.getName());
        assertEquals("Fiction", book.getGenre());
        assertEquals("Test Description", book.getDescription());
        assertEquals("Test Author", book.getAuthor());
    }

    @Test
    public void testEqualsAndHashCode() {
        Book book1 = new Book(1L, "1234567890", "Test Book", "Fiction", "Test Description", "Test Author");
        Book book2 = new Book(1L, "1234567890", "Test Book", "Fiction", "Test Description", "Test Author");
        Book book3 = new Book(2L, "0987654321", "Another Book", "Non-Fiction", "Another Description", "Another Author");

        assertEquals(book1, book2);
        assertNotEquals(book1, book3);
        assertEquals(book1.hashCode(), book2.hashCode());
        assertNotEquals(book1.hashCode(), book3.hashCode());
    }

    @Test
    public void testToString() {
        Book book = new Book(1L, "1234567890", "Test Book", "Fiction", "Test Description", "Test Author");
        String expectedString = "Book(id=1, isbn=1234567890, name=Test Book, genre=Fiction, description=Test Description, author=Test Author)";
        assertEquals(expectedString, book.toString());
    }
}
