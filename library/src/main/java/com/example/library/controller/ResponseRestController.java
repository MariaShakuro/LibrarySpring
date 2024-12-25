package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.library.service.BookService;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/books")
public class ResponseRestController {
    @Autowired
    public BookService bookService;
    private static final Logger logger = LoggerFactory.getLogger(ResponseRestController.class);

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    List<Book>getAllBooks(@RequestHeader("Authorization")String token) throws UnauthorizedException {
        return bookService.getAllBooks(token);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
   ResponseEntity<Book>getBookById(@PathVariable Long id,@RequestHeader("Authorization")String token) throws UnauthorizedException {
        Optional<Book>book=bookService.getBookById(id,token);
        return book.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/isbn/{isbn}")
    ResponseEntity<Book>getBookByIsbn(@PathVariable String isbn,@RequestHeader("Authorization")String token) throws UnauthorizedException {
        Optional<Book>book=bookService.getBookByIsbn(isbn,token);
        return book.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    Mono<ResponseEntity<?>> addBook(@RequestBody Book book,@RequestHeader("Authorization")String token) {
        logger.info("Received request to add book with ISBN: {}", book.getIsbn());
        try{
            Book createdBook=bookService.addBook(book,token);
            logger.info("Book created successfully with ID: {}", createdBook.getId());
            return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(createdBook));
        }catch(IllegalArgumentException e){
            logger.error("Conflict error: {}", e.getMessage(), e);
            return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()));
        }catch(Exception | UnauthorizedException e){
             logger.error("Internal error: {}", e.getMessage(), e);
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error"));
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    ResponseEntity<?>updateBook(@PathVariable Long id, @RequestBody Map<String,Object>updates,@RequestHeader("Authorization")String token) throws UnauthorizedException {
        Book book = bookService.getBookById(id,token).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getIsbn() == null || book.getIsbn().isEmpty() || book.getName() == null || book.getName().isEmpty() || book.getGenre() == null || book.getGenre().isEmpty() || book.getDescription() == null || book.getDescription().isEmpty() || book.getAuthor() == null || book.getAuthor().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect data for book");
        }
        updates.forEach((key, value) -> {
            Field field;
            try {
                 field = Book.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(book, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error with update the field"+ key,e);
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field, book, value);
        });
        bookService.updateBook(id,book,token);
        return ResponseEntity.ok(book);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<?>deleteBook(@PathVariable Long id,@RequestHeader("Authorization")String token){
        try{
            if(!bookService.getBookById(id,token).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You already deleted the book");
            }
            bookService.deleteBook(id,token);
            return ResponseEntity.noContent().build();
        }catch(Exception | UnauthorizedException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error");
        }
    }
}
