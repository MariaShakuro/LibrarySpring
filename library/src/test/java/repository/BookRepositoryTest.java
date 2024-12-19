package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testSaveAndFindById() {
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setName("Test Book");
        book.setGenre("Fiction");
        book.setDescription("A test book description");
        book.setAuthor("Author Name");

        Book savedBook = bookRepository.save(book);
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getId()).isEqualTo(savedBook.getId());
    }

    @Test
    void testFindByIsbn() {
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setName("Test Book");
        book.setGenre("Fiction");
        book.setDescription("A test book description");
        book.setAuthor("Author Name");

        bookRepository.save(book);
        Optional<Book> foundBook = bookRepository.getBookByIsbn("1234567890");

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getIsbn()).isEqualTo("1234567890");
    }
}
