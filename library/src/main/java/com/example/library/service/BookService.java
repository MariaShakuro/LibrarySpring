package com.example.library.service;

import com.example.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.library.repository.BookRepository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private WebClient webClient;
    public List<Book>getAllBooks(){
        return bookRepository.findAll();
    }
    public Optional<Book>getBookById(Long id){
        return bookRepository.findById(id);
    }
    public Optional<Book>getBookByIsbn(String isbn){
        return bookRepository.getBookByIsbn(isbn);
    }
    public Mono<Void>notifyBook(Long id,Book book){
        return webClient.post()
                .uri("http://localhost:8080/api/freebooks")
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Void.class);
    }
    public Book addBook(Book book){
        Optional<Book> existingBook = bookRepository.getBookByIsbn(book.getIsbn());
        if(existingBook.isPresent()) {
            throw new IllegalArgumentException("The book already existed with this isbn");
        }
        return bookRepository.save(book);
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
