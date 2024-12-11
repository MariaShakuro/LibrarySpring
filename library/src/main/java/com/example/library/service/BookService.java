package com.example.library.service;

import com.example.library.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.library.repository.BookRepository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    private static final Logger logger= LoggerFactory.getLogger(BookService.class);
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    private String getBearerToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJleGFtcGxlVXNlciIsImlhdCI6MTUxNjIzOTAyMn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
         }
    public List<Book>getAllBooks(){
        return bookRepository.findAll();
    }
    public Optional<Book>getBookById(Long id){
        return bookRepository.findById(id);
    }
    public Optional<Book>getBookByIsbn(String isbn){
        return bookRepository.getBookByIsbn(isbn);
    }
    public Book addBook(Book book){
        Optional<Book> existingBook = bookRepository.getBookByIsbn(book.getIsbn());
        if(existingBook.isPresent()) {
            throw new IllegalArgumentException("The book already existed with this isbn");
        }
        Book createdBook=bookRepository.save(book);
        String token = getBearerToken();
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/freebooks")
                .header("Authorization", "Bearer " + token)
                .bodyValue(createdBook)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(error -> logger.error("Failed to notify libraryservice: {}", error.getMessage()))
                .doOnSuccess(response -> logger.info("Successfully notified libraryservice"))
                .subscribe();
        return createdBook;//bookRepository.save(book);
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
    public Book updateBook(Long id,Book details){
        Book book=bookRepository.findById(id).orElseThrow(()->new RuntimeException("Book not found"));
        book.setName(details.getName());
        book.setIsbn(details.getIsbn());
        book.setGenre(details.getGenre());
        book.setDescription(details.getDescription());
        book.setAuthor(details.getAuthor());
        return bookRepository.save(book);
    }

}
