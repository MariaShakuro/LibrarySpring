package libraryservice.libraryservice.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@DisplayName("Test for BookConsumer")
public class BookConsumerTest {

    @Mock
    private BookInfoRepository bookInfoRepository;

    @InjectMocks
    private BookConsumer bookConsumer;

    @Test
    @DisplayName("Should handle delete book")
    void shouldHandleDeleteBook() {
        Long bookId = 1L;
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookId(bookId);

        when(bookInfoRepository.findByBookId(bookId)).thenReturn(Optional.of(bookInfo));

        bookConsumer.handleDeleteBook(bookId);

        verify(bookInfoRepository).findByBookId(bookId);
        verify(bookInfoRepository).delete(bookInfo);
    }
}


