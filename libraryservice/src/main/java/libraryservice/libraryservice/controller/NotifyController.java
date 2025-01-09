package libraryservice.libraryservice.controller;

import libraryservice.libraryservice.dto.BookInfoDto;
import libraryservice.libraryservice.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book_info")
public class NotifyController {
    @Autowired
    private BookInfoService bookInfoService;


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/available")
    public ResponseEntity<List<BookInfoDto>> getAvailableBooks() {
        List<BookInfoDto> availableBooks = bookInfoService.getAvailableBooks();
        return ResponseEntity.ok(availableBooks);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/updateStatus")
    public ResponseEntity<Void> updateBookStatus(@RequestBody BookInfoDto bookInfoDto) {
        bookInfoService.updateBookStatus(bookInfoDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<BookInfoDto>> getAllBooksInfo() {
        List<BookInfoDto> booksInfo = bookInfoService.getAllBooksInfo();
        return ResponseEntity.ok(booksInfo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{bookId}")
    public ResponseEntity<BookInfoDto> updateBookInfo(@PathVariable Long bookId, @RequestBody BookInfoDto details) {
        BookInfoDto updatedBook = bookInfoService.updateBookInfo(bookId, details);
        return ResponseEntity.ok(updatedBook);
    }
}
