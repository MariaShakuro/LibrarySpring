package libraryservice.libraryservice.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.annotation.KafkaListener;

@ExtendWith(MockitoExtension.class)
public class BookConsumerTest {

    @Mock
    private BookInfoRepository bookInfoRepository;

    @InjectMocks
    private BookConsumer bookConsumer;
    private BookInfo bookInfo;
    @BeforeEach
    void setUp() {
    }

    @Test
    void testHandleDeleteBook() {
        Long bookId = 1L;
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookId(bookId);
        when(bookInfoRepository.findByBookId(bookId)).thenReturn(Optional.of(bookInfo));

        bookConsumer.handleDeleteBook(bookId);

        verify(bookInfoRepository, times(1)).findByBookId(bookId);
        verify(bookInfoRepository, times(1)).save(bookInfo);
    }
}
