package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookMapper;
import com.example.library.entity.Book;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookService {

    BookRepository bookRepository;
    BookProducer bookProducer;

    public List<BookDto> getAllBooks() {
        log.info("Fetching all books");
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public Optional<BookDto> getBookById(Long id) {
        log.info("Fetching book with ID: {}", id);
        return bookRepository.findById(id).map(BookMapper.INSTANCE::toDto);
    }

    public Optional<BookDto> getBookByIsbn(String isbn) {
        log.info("Fetching book with ISBN: {}", isbn);
        return bookRepository.getBookByIsbn(isbn).map(BookMapper.INSTANCE::toDto);
    }

    public BookDto addBook(BookDto bookDto) {
        log.info("Attempting to add a new book with ISBN: {}", bookDto.getIsbn());
        Optional<Book> existingBook = bookRepository.getBookByIsbn(bookDto.getIsbn());
        if (existingBook.isPresent()) {
            throw new IllegalArgumentException("The book already existed with this isbn");
        }
        try {
            Book book = BookMapper.INSTANCE.toEntity(bookDto);
            book.setDeleted(false);
            Book createdBook = bookRepository.save(book);
            log.info("Book with ISBN: {} added successfully", bookDto.getIsbn());
            bookProducer.sendBookEvent("new-book-topic", createdBook.getId());
            return BookMapper.INSTANCE.toDto(createdBook);
        } catch (Exception e) {
            throw new RuntimeException("Error saving book", e);
        }
    }

    public void deleteBook(Long id) {
        log.info("Attempting to delete book with ID: {}", id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            log.info("Book with ID: {} deleted successfully", id);
            bookProducer.sendBookEvent("delete-book-topic", id);
        } else throw new RuntimeException("Book not found");
    }

    public Optional<BookDto> updateBook(Long id, BookDto bookDto) throws UnauthorizedException {
        log.info("Attempting to update book with ID: {}", id);
        Optional<Book>existedBook=bookRepository.findById(id);
        if(existedBook.isPresent()) {
            Book book = existedBook.get();
            bookDto.setName(bookDto.getName());
            bookDto.setIsbn(bookDto.getIsbn());
            bookDto.setGenre(bookDto.getGenre());
            bookDto.setDescription(bookDto.getDescription());
            bookDto.setAuthor(bookDto.getAuthor());
            Book updatedBook = bookRepository.save(book);
            return Optional.of(BookMapper.INSTANCE.toDto(updatedBook));
        }
        return Optional.empty();
    }

}
