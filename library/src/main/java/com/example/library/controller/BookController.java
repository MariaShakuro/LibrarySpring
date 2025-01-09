package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
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
public class BookController {
    @Autowired
    public BookService bookService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        Optional<BookDto> bookDto = bookService.getBookById(id);
        return bookDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/isbn/{isbn}")
    ResponseEntity<BookDto> getBookByIsbn(@PathVariable String isbn) {
        Optional<BookDto> bookDto = bookService.getBookByIsbn(isbn);
        return bookDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    Mono<ResponseEntity<?>> addBook(@RequestBody BookDto bookDto) {
        BookDto createdBook = bookService.addBook(bookDto);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(createdBook));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        BookDto bookDto = bookService.getBookById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        if (!isValidBookData(bookDto)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect data for book");
        }
        updates.forEach((key, value) -> {
            Field field;
            try {
                field = Book.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(bookDto, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error with update the field" + key, e);
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field, bookDto, value);
        });
        bookService.updateBook(id, bookDto);
        return ResponseEntity.ok(bookDto);
    }

    private boolean isValidBookData(BookDto bookDto) {
        return bookDto.getIsbn() != null && !bookDto.getIsbn().isEmpty() &&
                bookDto.getName() != null && !bookDto.getName().isEmpty() &&
                bookDto.getGenre() != null && !bookDto.getGenre().isEmpty() &&
                bookDto.getDescription() != null && !bookDto.getDescription().isEmpty() &&
                bookDto.getAuthor() != null && !bookDto.getAuthor().isEmpty();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (!bookService.getBookById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You already deleted the book");
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
