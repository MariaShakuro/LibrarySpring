package libraryservice.libraryservice.controller;

import libraryservice.libraryservice.dto.BookInfoDTO;
import libraryservice.libraryservice.entity.BookInfo;
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
    public ResponseEntity<List<BookInfoDTO>> getAvailableBooks(@RequestHeader("Authorization")String token) {
        List<BookInfoDTO> availableBooks = bookInfoService.getAvailableBooks(token);
        return ResponseEntity.ok(availableBooks);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/updateStatus")
    public ResponseEntity<Void> updateBookStatus(@RequestHeader("Authorization")String token, @RequestBody BookInfoDTO bookInfoDTO) {
        bookInfoService.updateBookStatus(token,bookInfoDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<BookInfo>>getAllBookInfo(@RequestHeader("Authorization")String token){
        List<BookInfo>booksInfo=bookInfoService.getAllBookInfo(token);
        return ResponseEntity.ok(booksInfo);
    }

    @PreAuthorize("hasRole('ADMIN')")
   @PatchMapping("/{bookId}")
    public ResponseEntity<BookInfo> updateBookInfo(@PathVariable Long bookId, @RequestBody BookInfo details){
        try{
            BookInfo updatedBook=bookInfoService.updateBookInfo(bookId,details);
            return ResponseEntity.ok(updatedBook);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
   }
}
