package com.example.library.service;


import com.example.library.entity.BookInfo;
import com.example.library.repository.BookInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class BookInfoService {
    @Autowired
    private BookInfoRepository bookInfoRepository;
    @Autowired
    private BookInfo bookInfo;
    public BookInfo saveBookInfo(Long idBook){
        bookInfo.setId(idBook);
        bookInfo.setBorrowTime(LocalDateTime.now());
        bookInfo.setReturnTime(LocalDateTime.now().plusWeeks(2));
        return bookInfoRepository.save(bookInfo);
    }
}
