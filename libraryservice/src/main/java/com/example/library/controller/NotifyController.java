package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/freebooks")
public class NotifyController {
    @Autowired
    private BookInfoService bookInfoService;
    @PostMapping
    public ResponseEntity<Void> receiveBookInfo(@RequestBody Book book){
      bookInfoService.saveBookInfo(book.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
}
}
