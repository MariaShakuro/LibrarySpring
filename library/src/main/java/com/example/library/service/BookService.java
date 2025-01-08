package com.example.library.service;

import com.example.library.client.JwtAuthClient;
import com.example.library.dto.BookDto;
import com.example.library.dto.BookMapper;
import com.example.library.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookProducer bookProducer;

    public List<BookDto>getAllBooks()  {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookMapper.INSTANCE::toDto).toList();
    }
    public Optional<BookDto>getBookById(Long id) {
        return bookRepository.findById(id).map(BookMapper.INSTANCE::toDto);
    }
    public Optional<BookDto>getBookByIsbn(String isbn) {
         return bookRepository.getBookByIsbn(isbn).map(BookMapper.INSTANCE::toDto);
    }
    public BookDto addBook(BookDto bookDto)  {
      //  log.info("Starting addBook method with book ISBN: {} and JWT token",book.getIsbn());
            Optional<Book> existingBook = bookRepository.getBookByIsbn(bookDto.getIsbn());
            if (existingBook.isPresent()) {
                throw new IllegalArgumentException("The book already existed with this isbn");
            }
            try {
              //  log.info("Saving new book...");
                Book book = BookMapper.INSTANCE.toEntity(bookDto);
                Book createdBook=bookRepository.save(book);
               // log.info("Book saved successfully with ID: {}", createdBook.getId());
             //   log.info("Sending book event to Kafka...");
                bookProducer.sendBookEvent("new-book-topic", createdBook.getId());
              //  log.info("Book event sent successfully");
                return BookMapper.INSTANCE.toDto(createdBook);
                //bookRepository.save(book);
            }catch(Exception e) {
                throw new RuntimeException("Error saving book", e);
            }
        }

    public void deleteBook(Long id)  {

        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            bookProducer.sendBookEvent("delete-book-topic", id);
        } else throw new RuntimeException("Book not found");
    }
    public BookDto updateBook(Long id,BookDto details) throws UnauthorizedException {
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
