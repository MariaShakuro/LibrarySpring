package com.example.library.controller;

import com.example.library.dto.BookDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.library.service.BookService;
import reactor.core.publisher.Mono;


import java.util.List;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookController {
    @Autowired
    public BookService bookService;

    @Operation(summary = "Get all books", description = "Fetches all books in the library")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    List<BookDto> getAllBooks() {
        log.info("Fetching all books");
        return bookService.getAllBooks();
    }

    @Operation(summary = "Get book by ID", description = "Fetches a book by its ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) {
        log.info("Fetching book with ID: {}", id);
        Optional<BookDto> bookDto = bookService.getBookById(id);
        return bookDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get book by ISBN", description = "Fetches a book by its ISBN")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/isbn/{isbn}")
    ResponseEntity<BookDto> getBookByIsbn(@PathVariable("isbn") String isbn) {
        log.info("Fetching book with ISBN: {}", isbn);
        Optional<BookDto> bookDto = bookService.getBookByIsbn(isbn);
        return bookDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new book", description = "Adds a new book to the library")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping
    Mono<ResponseEntity<?>> addBook(@RequestBody BookDto bookDto) {
        log.info("Adding a new book: {}", bookDto.getName());
        BookDto createdBook = bookService.addBook(bookDto);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(createdBook));

    }

    @Operation(summary = "Update book", description = "Updates the details of an existing book")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
        log.info("Updating book with ID: {}", id);
        return bookService.updateBook(id, bookDto)
                .map(updatedBook -> new ResponseEntity<>(updatedBook, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Delete book", description = "Deletes a book by its ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        log.info("Deleting book with ID: {}", id);
        if (!bookService.getBookById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You already deleted the book");
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
