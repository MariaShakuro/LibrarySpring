package libraryservice.libraryservice.service;

import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookConsumer {
    private final BookInfoRepository bookInfoRepository;

    @Autowired
    public BookConsumer(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }

    @KafkaListener(topics = "new-book-topic", groupId = "libraryservice-group")
    public void handleNewBook(Long bookId) {
        Optional<BookInfo> existingBookInfo = bookInfoRepository.findByBookId(bookId);
        if (existingBookInfo.isPresent()) {
            BookInfo bookInfo = existingBookInfo.get();
            bookInfo.setStatus("available");
            bookInfoRepository.save(bookInfo);
        } else {
            BookInfo newBookInfo = new BookInfo();
            newBookInfo.setBookId(bookId);
            newBookInfo.setStatus("available");

            bookInfoRepository.save(newBookInfo);
        }
    }

    @KafkaListener(topics = "delete-book-topic", groupId = "libraryservice-group")
    public void handleDeleteBook(Long bookId) {
        BookInfo bookInfo = bookInfoRepository.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookInfoRepository.delete(bookInfo);
    }

}




