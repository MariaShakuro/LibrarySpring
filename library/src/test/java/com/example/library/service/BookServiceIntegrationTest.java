package com.example.library.service;

import com.example.library.BaseTest;
import com.example.library.dto.BookDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Integration Tests for BookService")
@ActiveProfiles("test")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class BookServiceIntegrationTest extends BaseTest {


    BookService bookService;

    @Test
    @DisplayName("Should Get All Books")
    void shouldGetAllBooks() {
        var books = bookService.getAllBooks();
        assertThat(books).isNotEmpty();
    }

    @Test
    @DisplayName("Should Get Book By ID")
    void shouldGetBookById() {
        Long bookId = 1L;
        Optional<BookDto> bookDto = bookService.getBookById(bookId);
        assertThat(bookDto).isPresent();
        assertThat(bookDto.get().getId()).isEqualTo(bookId);
    }

    @Test
    @DisplayName("Should Get Book By ISBN")
    void shouldGetBookByIsbn() {
        String isbn = "1234567890123";
        Optional<BookDto> bookDto = bookService.getBookByIsbn(isbn);
        assertThat(bookDto).isPresent();
        assertThat(bookDto.get().getIsbn()).isEqualTo(isbn);
    }

    @Test
    @DisplayName("Should Add Book")
    void shouldAddBook() {
        BookDto bookDto = BookDto.builder()
                .name("New Book")
                .isbn("1234567890123")
                .genre("Fiction")
                .description("A great book")
                .author("Author Name")
                .build();

        BookDto createdBook = bookService.addBook(bookDto);
        assertThat(createdBook.getName()).isEqualTo("New Book");
    }

    @Test
    @DisplayName("Should Update Book")
    void shouldUpdateBook() {
        Long bookId = 1L;
        BookDto bookDto = BookDto.builder()
                .name("Updated Book")
                .isbn("1234567890123")
                .genre("Updated Genre")
                .description("Updated Description")
                .author("Updated Author")
                .build();

        BookDto updatedBook = bookService.updateBook(bookId, bookDto);
        assertThat(updatedBook.getName()).isEqualTo("Updated Book");
    }

    @Test
    @DisplayName("Should Delete Book")
    void shouldDeleteBook() {
        Long bookId = 1L;

        bookService.deleteBook(bookId);
        Optional<BookDto> deletedBook = bookService.getBookById(bookId);
        assertThat(deletedBook).isEmpty();
    }

    @Test
    @DisplayName("Should Throw Exception When Adding Duplicate Book")
    void shouldThrowExceptionWhenAddingDuplicateBook() {
        BookDto bookDto = BookDto.builder()
                .isbn("1234567890123")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.addBook(bookDto);
        });

        assertThat(exception.getMessage()).isEqualTo("The book already existed with this isbn");
    }
}
