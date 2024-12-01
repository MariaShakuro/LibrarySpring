package service;

import entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public List<Book>getAllBooks(){
        return bookRepository.findAll();
    }
    public Optional<Book>getBookById(Long id){
        return bookRepository.findById(id);
    }
    public Optional<Book>getBookByISBN(String isbn){
        return bookRepository.getBookByISBN(isbn);
    }
    public Book addBook(Book book){
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
