package libraryservice.libraryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import libraryservice.libraryservice.dto.BookInfoDto;
import libraryservice.libraryservice.service.BookInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/book_info")
@Tag(name = "Book_info", description = "Operations pertaining to book information")
public class NotifyController {
    @Autowired
    private BookInfoService bookInfoService;

    @Operation(summary = "Get available books", description = "Fetches available books")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/available")
    public ResponseEntity<List<BookInfoDto>> getAvailableBooks() {
        log.info("Fetching available books");
        List<BookInfoDto> availableBooks = bookInfoService.getAvailableBooks();
        return ResponseEntity.ok(availableBooks);
    }

    @Operation(summary = "Update book status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PatchMapping("/updateStatus")
    public ResponseEntity<Void> updateBookStatus(@RequestBody BookInfoDto bookInfoDto) {
        bookInfoService.updateBookStatus(bookInfoDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Get all books info")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<BookInfoDto>> getAllBooksInfo() {
        log.info("Fetching all book information");
        List<BookInfoDto> booksInfo = bookInfoService.getAllBooksInfo();
        return ResponseEntity.ok(booksInfo);
    }

    @Operation(summary = "Update book info")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PatchMapping("/{bookId}")
    public ResponseEntity<BookInfoDto> updateBookInfo(@PathVariable("bookId") Long bookId, @RequestBody BookInfoDto details) {
        log.info("Updating information for book with ID: {}", bookId);
        BookInfoDto updatedBook = bookInfoService.updateBookInfo(bookId, details);
        return ResponseEntity.ok(updatedBook);
    }
}
