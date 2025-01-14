package libraryservice.libraryservice.service;
/*
import static org.mockito.Mockito.*;

import java.util.Optional;

import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("Unit Tests for BookConsumer")
public class BookConsumerTest {

    @Mock
    private BookInfoRepository bookInfoRepository;

    @InjectMocks
    private BookConsumer bookConsumer;


    @Test
    @DisplayName("Should Handle Delete Book")
    void testHandleDeleteBook() {
        Long bookId = 1L;
        BookInfo bookInfo = BookInfo.builder()
                .bookId(bookId)
                .build();
        when(bookInfoRepository.findByBookId(bookId)).thenReturn(Optional.of(bookInfo));

        bookConsumer.handleDeleteBook(bookId);

        verify(bookInfoRepository, times(1)).findByBookId(bookId);
        verify(bookInfoRepository, times(1)).save(bookInfo);
    }
}
*/