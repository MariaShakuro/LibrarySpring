package libraryservice.libraryservice.controller;

import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/freebooks")
public class NotifyController {
    @Autowired
    private BookInfoService bookInfoService;
   /* @GetMapping("/{idBook}")
    public ResponseEntity<BookInfo> getBookInfo(@RequestBody Book idBook) {
        try {
            BookInfo bookInfo = bookInfoService.getBookInfoById(idBook.getId());
            return ResponseEntity.ok(bookInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }*/
    @GetMapping
    public ResponseEntity<List<BookInfo>>getAllBookInfo(){
        List<BookInfo>booksInfo=bookInfoService.getAllBookInfo();
        return ResponseEntity.ok(booksInfo);
    }
    @PostMapping
    public ResponseEntity<Void>receiveBookInfo(@RequestBody Long idBook){
        bookInfoService.saveBookInfo(idBook);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
   @PatchMapping("/{idBook}")
    public ResponseEntity<BookInfo> updateBookInfo(@PathVariable Long idBook, @RequestBody BookInfo details){
        try{
            BookInfo updatedBook=bookInfoService.updateBookInfo(idBook,details);
            return ResponseEntity.ok(updatedBook);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
   }
}
