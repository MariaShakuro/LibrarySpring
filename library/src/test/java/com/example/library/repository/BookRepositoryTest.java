package com.example.library.repository;
/*

import java.util.Optional;

import com.example.library.entity.Book;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("Unit Tests for BookRepository")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookRepositoryTest {


    BookRepository bookRepository;

    @Test
    @DisplayName("Should Find Book By ISBN")
    void shouldFindBookByIsbn() {
        Book book = Book.builder()
                .name("Test Book")
                .isbn("1234567890123")
                .genre("Genre")
                .description("Description")
                .author("Author")
                .build();

        bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.getBookByIsbn("1234567890123");

        assertTrue(foundBook.isPresent(), "Book should be found");
        assertThat(foundBook.get().getIsbn()).isEqualTo("1234567890123");
    }
}

*/