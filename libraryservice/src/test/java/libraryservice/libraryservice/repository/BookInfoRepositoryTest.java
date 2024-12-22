package libraryservice.libraryservice.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import libraryservice.libraryservice.entity.BookInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class BookInfoRepositoryTest {

    @Mock
    private BookInfoRepository bookInfoRepository;

    private BookInfo bookInfo1;
    private BookInfo bookInfo2;

    @BeforeEach
    void setUp() {
        bookInfo1 = new BookInfo();
        bookInfo1.setBookId(1L);
        bookInfo1.setStatus("available");

        bookInfo2 = new BookInfo();
        bookInfo2.setBookId(2L);
        bookInfo2.setStatus("checked_out");
    }

    @Test
    void testFindByBookId() {
        when(bookInfoRepository.findByBookId(1L)).thenReturn(Optional.of(bookInfo1));

        Optional<BookInfo> result = bookInfoRepository.findByBookId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getBookId());
    }

    @Test
    void testFindByStatus() {
        when(bookInfoRepository.findByStatus("available")).thenReturn(List.of(bookInfo1));

        List<BookInfo> result = bookInfoRepository.findByStatus("available");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("available", result.get(0).getStatus());
    }
}

