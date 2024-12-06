package com.example.library.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.entity.BookInfo;
import com.example.library.repository.BookInfoRepository;
import com.example.library.service.BookInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookInfoServiceTest {

    @Mock
    private BookInfoRepository bookInfoRepository;

    @Mock
    private BookInfo bookInfo;

    @InjectMocks
    private BookInfoService bookInfoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveBookInfo() {
        Long idBook = 1L;
        LocalDateTime now = LocalDateTime.now();

        when(bookInfoRepository.save(any(BookInfo.class))).thenAnswer(invocation -> {
            BookInfo book = invocation.getArgument(0);
            book.setId(1L);
            book.setBorrowTime(now);
            book.setReturnTime(now.plusWeeks(2));
            return book;
        });

        BookInfo savedBookInfo = bookInfoService.saveBookInfo(idBook);

        assertEquals(idBook, savedBookInfo.getId());
        assertEquals(now, savedBookInfo.getBorrowTime());
        assertEquals(now.plusWeeks(2), savedBookInfo.getReturnTime());

        verify(bookInfoRepository, times(1)).save(any(BookInfo.class));
    }
}
