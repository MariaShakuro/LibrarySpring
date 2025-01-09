package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookMapper;
import com.example.library.entity.Book;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import com.example.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookService {

    BookRepository bookRepository;
    BookProducer bookProducer;

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookMapper.INSTANCE::toDto).toList();
    }

    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id).map(BookMapper.INSTANCE::toDto);
    }

    public Optional<BookDto> getBookByIsbn(String isbn) {
        return bookRepository.getBookByIsbn(isbn).map(BookMapper.INSTANCE::toDto);
    }

    public BookDto addBook(BookDto bookDto) {
        Optional<Book> existingBook = bookRepository.getBookByIsbn(bookDto.getIsbn());
        if (existingBook.isPresent()) {
            throw new IllegalArgumentException("The book already existed with this isbn");
        }
        try {
            Book book = BookMapper.INSTANCE.toEntity(bookDto);
            Book createdBook = bookRepository.save(book);
            bookProducer.sendBookEvent("new-book-topic", createdBook.getId());
            return BookMapper.INSTANCE.toDto(createdBook);
        } catch (Exception e) {
            throw new RuntimeException("Error saving book", e);
        }
    }

    public void deleteBook(Long id) {

        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            bookProducer.sendBookEvent("delete-book-topic", id);
        } else throw new RuntimeException("Book not found");
    }

    public BookDto updateBook(Long id, BookDto details) throws UnauthorizedException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setName(details.getName());
        book.setIsbn(details.getIsbn());
        book.setGenre(details.getGenre());
        book.setDescription(details.getDescription());
        book.setAuthor(details.getAuthor());
        Book updatedBook = bookRepository.save(book);
        return BookMapper.INSTANCE.toDto(updatedBook);
    }

}
