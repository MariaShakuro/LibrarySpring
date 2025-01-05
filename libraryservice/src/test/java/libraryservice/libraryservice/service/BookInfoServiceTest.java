package libraryservice.libraryservice.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import libraryservice.libraryservice.client.JwtAuthClient;
import libraryservice.libraryservice.dto.BookInfoDto;
import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookInfoServiceTest {

    @Mock
    private BookInfoRepository bookInfoRepository;

    @Mock
    private JwtAuthClient jwtAuthClient;

    @InjectMocks
    private BookInfoService bookInfoService;
    private BookInfo bookInfo;
    @BeforeEach
    void setUp() {
        bookInfo=new BookInfo();
        bookInfo.setBookId(1L);
    }

    @Test
    void testGetAvailableBooks_ValidToken() {
        String validToken = "valid-jwt-token";
        when(jwtAuthClient.validateToken("Bearer " + validToken)).thenReturn(true);
        when(bookInfoRepository.findByStatus("available")).thenReturn(Arrays.asList(new BookInfo()));

        List<BookInfoDto> availableBooks = bookInfoService.getAvailableBooks(validToken);

        assertNotNull(availableBooks);
        assertEquals(1, availableBooks.size());
        verify(bookInfoRepository, times(1)).findByStatus("available");
    }

    @Test
    void testGetAvailableBooks_InvalidToken() {
        String invalidToken = "invalid-jwt-token";
        when(jwtAuthClient.validateToken("Bearer " + invalidToken)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            bookInfoService.getAvailableBooks(invalidToken);
        });

        verify(bookInfoRepository, never()).findByStatus(anyString());
    }

    @Test
    void testUpdateBookStatus_ValidToken() {
        String validToken = "valid-jwt-token";
        BookInfoDto bookInfoDTO = new BookInfoDto(1L, "available", null, null, false);
        BookInfo bookInfo = new BookInfo();

        when(jwtAuthClient.validateToken("Bearer " + validToken)).thenReturn(true);
        when(bookInfoRepository.findByBookId(1L)).thenReturn(Optional.of(bookInfo));

        bookInfoService.updateBookStatus(validToken, bookInfoDTO);

        assertEquals("available", bookInfo.getStatus());
        verify(bookInfoRepository, times(1)).save(bookInfo);
    }

    @Test
    void testUpdateBookStatus_InvalidToken() {
        String invalidToken = "invalid-jwt-token";
        BookInfoDto bookInfoDTO = new BookInfoDto(1L, "available", null, null, false);

        when(jwtAuthClient.validateToken("Bearer " + invalidToken)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            bookInfoService.updateBookStatus(invalidToken, bookInfoDTO);
        });

        verify(bookInfoRepository, never()).save(any(BookInfo.class));
    }

    @Test
    void testGetAllBookInfo_ValidToken() {
        String validToken = "valid-jwt-token";
        when(jwtAuthClient.validateToken("Bearer " + validToken)).thenReturn(true);
        when(bookInfoRepository.findAll()).thenReturn(Arrays.asList(new BookInfo()));

        List<BookInfo> bookInfos = bookInfoService.getAllBooksInfo(validToken);

        assertNotNull(bookInfos);
        assertEquals(1, bookInfos.size());
        verify(bookInfoRepository, times(1)).findAll();
    }

    @Test
    void testGetAllBookInfo_InvalidToken() {
        String invalidToken = "invalid-jwt-token";
        when(jwtAuthClient.validateToken("Bearer " + invalidToken)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            bookInfoService.getAllBooksInfo(invalidToken);
        });

        verify(bookInfoRepository, never()).findAll();
    }

    @Test
    void testUpdateBookInfo() {
        Long id = 1L;
        BookInfo details = new BookInfo();
        details.setBorrowTime(LocalDateTime.now());
        details.setReturnTime(LocalDateTime.now().plusWeeks(2));
        details.setStatus("available");


        when(bookInfoRepository.findById(id)).thenReturn(Optional.of(bookInfo));
        when(bookInfoRepository.save(bookInfo)).thenReturn(bookInfo);

        BookInfo updatedBookInfo = bookInfoService.updateBookInfo(id, details);

        assertNotNull(updatedBookInfo);
        assertEquals("available", updatedBookInfo.getStatus());
        assertNotNull(updatedBookInfo.getBorrowTime());
        assertNotNull(updatedBookInfo.getReturnTime());
        verify(bookInfoRepository,times(1)).findById(bookInfo.getBookId());
        verify(bookInfoRepository, times(1)).save(bookInfo);
    }
}


