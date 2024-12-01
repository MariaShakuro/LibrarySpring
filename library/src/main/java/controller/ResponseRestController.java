package controller;

import entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class ResponseRestController {
    @Autowired
    public BookService bookService;
    @GetMapping
    List<Book>getAllBooks(){
        return bookService.getAllBooks();
    }
    @GetMapping("/{id}")
   ResponseEntity<Book>getBookById(@PathVariable Long id) {
        Optional<Book>book=bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @GetMapping("/isbn/{isbn}")
    ResponseEntity<Book>getBookByISBN(@PathVariable String isbn){
        Optional<Book>book=bookService.getBookByISBN(isbn);
        return book.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @PostMapping
    Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }
    @PutMapping("/{id}")
    ResponseEntity<Book>updateBook(@PathVariable Long id,@RequestBody Book details){
        Book updateBook=bookService.updateBook(id,details);
        return ResponseEntity.ok(updateBook);
    }
    @PutMapping("/{id}")
    ResponseEntity<Book>deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
