package com.example.library.service;

import com.example.library.client.JwtAuthClient;
import com.example.library.entity.Book;
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
    private static final Logger logger= LoggerFactory.getLogger(BookService.class);
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    BookProducer bookProducer;
    @Autowired
    private JwtAuthClient jwtAuthClient;

    public List<Book>getAllBooks(String jwtToken) throws UnauthorizedException {
        String bearerToken = "Bearer " + jwtToken.trim();
        Boolean isValidToken = jwtAuthClient.validateToken(bearerToken);
        if (isValidToken) {
            return bookRepository.findAll().stream()
                    .filter(book -> !book.isDeleted())
                    .collect(Collectors.toList());
        }else{
           throw new UnauthorizedException("Invalid JWT token");
        }
    }
    public Optional<Book>getBookById(Long id,String jwtToken) throws UnauthorizedException {
        String bearerToken = "Bearer " + jwtToken.trim();
        Boolean isValidToken = jwtAuthClient.validateToken(bearerToken);
        if(isValidToken) return bookRepository.findById(id);
        else throw new UnauthorizedException("Invalid JWT token");
    }
    public Optional<Book>getBookByIsbn(String isbn,String jwtToken) throws UnauthorizedException {
        String bearerToken = "Bearer " + jwtToken.trim();
        Boolean isValidToken = jwtAuthClient.validateToken(bearerToken);
        if(isValidToken) return bookRepository.getBookByIsbn(isbn);
        else throw new UnauthorizedException("Invalid JWT token");
    }
    public Book addBook(Book book,String jwtToken) throws UnauthorizedException {
        String bearerToken = "Bearer " + jwtToken.trim();
        Boolean isValidToken = jwtAuthClient.validateToken(bearerToken);
        if (isValidToken) {
            Optional<Book> existingBook = bookRepository.getBookByIsbn(book.getIsbn());
            if (existingBook.isPresent()) {
                throw new IllegalArgumentException("The book already existed with this isbn");
            }
            Book createdBook = bookRepository.save(book);
            bookProducer.sendBookEvent("new-book-topic", createdBook.getId());

            return createdBook;//bookRepository.save(book);
        }else{
            throw new UnauthorizedException("Invalid JWT token");
        }
        }

    public void deleteBook(Long id,String jwtToken) throws UnauthorizedException {
        String bearerToken = "Bearer " + jwtToken.trim();
        Boolean isValidToken = jwtAuthClient.validateToken(bearerToken);
        if (isValidToken){
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            // Soft delete logic
            book.get().setDeleted(true);
            bookRepository.save(book.get());
            bookProducer.sendBookEvent("delete-book-topic", id);
        }else{
            throw new RuntimeException("Book not found");
        }
        }else{
            throw new UnauthorizedException("Invalid JWT token");
        }
        //bookRepository.deleteById(id);
    }
    public Book updateBook(Long id,Book details,String jwtToken) throws UnauthorizedException {
        String bearerToken = "Bearer " + jwtToken.trim();
        Boolean isValidToken = jwtAuthClient.validateToken(bearerToken);
        if (isValidToken) {
            Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
            book.setName(details.getName());
            book.setIsbn(details.getIsbn());
            book.setGenre(details.getGenre());
            book.setDescription(details.getDescription());
            book.setAuthor(details.getAuthor());
            return bookRepository.save(book);
        }else{
            throw new UnauthorizedException("Invalid JWT token");
        }
    }

}
