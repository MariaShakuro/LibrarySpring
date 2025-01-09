package libraryservice.libraryservice.repository;


import java.util.List;
import java.util.Optional;
import libraryservice.libraryservice.entity.BookInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Unit Tests for BookInfoRepository")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookInfoRepositoryTest {

    BookInfoRepository bookInfoRepository;
    BookInfo bookInfo;

    @BeforeEach
    void setUp() {
        BookInfo.builder()
                .status("available")
                .build();
        bookInfoRepository.save(bookInfo);
    }

    @Test
    @DisplayName("Should Find Book By Book ID")
    void shouldFindByBookId() {
        Long bookId = bookInfo.getBookId();
        Optional<BookInfo> foundBook = bookInfoRepository.findByBookId(bookId);

        assertTrue(foundBook.isPresent(), "Book should be found");
        assertThat(foundBook.get().getBookId()).isEqualTo(bookId);
    }

    @Test
    @DisplayName("Should Find Books By Status")
    void shouldFindByStatus() {
        String status = "available";
        List<BookInfo> availableBooks = bookInfoRepository.findByStatus(status);

        assertThat(availableBooks).isNotEmpty();
        assertThat(availableBooks.get(0).getStatus()).isEqualTo(status);
    }
}

