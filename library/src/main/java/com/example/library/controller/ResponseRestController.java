package com.example.library.controller;

import com.example.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.library.service.BookService;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.reflect.AccessibleObject.setAccessible;

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
    ResponseEntity<Book>getBookByIsbn(@PathVariable String isbn){
        Optional<Book>book=bookService.getBookByIsbn(isbn);
        return book.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @PostMapping
    Mono<ResponseEntity<?>> addBook(@RequestBody Book book) {
        try{
            Book createdBook=bookService.addBook(book);
            return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(createdBook));
        }catch(IllegalArgumentException e){
            return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()));
        }catch(Exception e){
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error"));
        }
    }
    @PatchMapping("/{id}")
    ResponseEntity<?>updateBook(@PathVariable Long id, @RequestBody Map<String,Object>updates){
        Book book = bookService.getBookById(id).orElseThrow(() -> new RuntimeException("Book not found"));
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
        bookService.updateBook(id,book);
        return ResponseEntity.ok(book);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?>deleteBook(@PathVariable Long id){
        try{
            if(!bookService.getBookById(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You already deleted the book");
            }
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error");
        }
    }
}
