package libraryservice.libraryservice.service;
/*
import libraryservice.libraryservice.BaseTest;
import libraryservice.libraryservice.dto.BookInfoDto;
import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Integration Tests for BookInfoService")
@ActiveProfiles("test")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookInfoServiceIntegrationTest extends BaseTest {

    BookInfoService bookInfoService;
    BookInfoRepository bookInfoRepository;

    @Test
    @DisplayName("Should Get Available Books")
    void shouldGetAvailableBooks() {
        BookInfo bookInfo = BookInfo.builder()
                .status("available")
                .build();
        bookInfoRepository.save(bookInfo);

        List<BookInfoDto> availableBooks = bookInfoService.getAvailableBooks();

        assertThat(availableBooks).isNotEmpty();
    }

    @Test
    @DisplayName("Should Update Book Status")
    void shouldUpdateBookStatus() {
        BookInfo bookInfo = BookInfo.builder()
                .status("available")
                .build();
        bookInfoRepository.save(bookInfo);

        BookInfoDto bookInfoDto = BookInfoDto.builder()
                .bookId(bookInfo.getBookId())
                .status("taken")
                .build();

        bookInfoService.updateBookStatus(bookInfoDto);

        Optional<BookInfo> updatedBookInfo = bookInfoRepository.findById(bookInfo.getBookId());
        assertThat(updatedBookInfo).isPresent();
        assertThat(updatedBookInfo.get().getStatus()).isEqualTo("taken");
    }

    @Test
    @DisplayName("Should Throw Exception When Updating Book With Invalid Status")
    void shouldThrowExceptionWhenUpdatingBookWithInvalidStatus() {
        BookInfoDto bookInfoDto = BookInfoDto.builder()
                .status("invalid_status")
                .build();

        assertThrows(IllegalArgumentException.class, () -> bookInfoService.updateBookStatus(bookInfoDto));
    }

    @Test
    @DisplayName("Should Get All Books Info")
    void shouldGetAllBooksInfo() {

        BookInfo bookInfo = BookInfo.builder()
                .build();
        bookInfoRepository.save(bookInfo);

        List<BookInfoDto> allBooksInfo = bookInfoService.getAllBooksInfo();

        assertThat(allBooksInfo).isNotEmpty();
    }

    @Test
    @DisplayName("Should Update Book Info")
    void shouldUpdateBookInfo() {

        BookInfo bookInfo = BookInfo.builder()
                .build();
        bookInfoRepository.save(bookInfo);

        BookInfoDto bookInfoDto = BookInfoDto.builder()
                .borrowTime(LocalDateTime.parse("2023-01-01T00:00:00"))
                .returnTime(LocalDateTime.parse("2023-01-10T00:00:00"))
                .status("taken")
                .build();

        BookInfoDto updatedBookInfo = bookInfoService.updateBookInfo(bookInfo.getBookId(), bookInfoDto);

        assertThat(updatedBookInfo.getBorrowTime()).isEqualTo("2023-01-01T00:00:00");
        assertThat(updatedBookInfo.getReturnTime()).isEqualTo("2023-01-10T00:00:00");
        assertThat(updatedBookInfo.getStatus()).isEqualTo("taken");
    }
}
*/