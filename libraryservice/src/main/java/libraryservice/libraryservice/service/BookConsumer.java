package libraryservice.libraryservice.service;

import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        //log.info("Received message from Kafka with book ID: " + bookId);
        try {
            Optional<BookInfo> existingBookInfo = bookInfoRepository.findByBookId(bookId);
            if (existingBookInfo.isPresent()) {
              //  log.warn("BookInfo with book ID " + bookId + " already exists, updating status.");
                BookInfo bookInfo = existingBookInfo.get();
                bookInfo.setStatus("available");
               // bookInfo.setBorrowTime(LocalDateTime.now());
               // bookInfo.setReturnTime(LocalDateTime.now().plusWeeks(2));
                bookInfoRepository.save(bookInfo);
            } else {
                BookInfo newBookInfo = new BookInfo();
                newBookInfo.setBookId(bookId); // Устанавливаем bookId как bookId
                newBookInfo.setStatus("available");
              //  newBookInfo.setBorrowTime(LocalDateTime.now());
               // newBookInfo.setReturnTime(LocalDateTime.now().plusWeeks(2));
                bookInfoRepository.save(newBookInfo);
            }
           // log.info("Saved BookInfo with book ID: " + bookId);
        } catch (Exception e) {
            //log.error("Failed to save BookInfo: " + e.getMessage(), e);
        }
    }

        @KafkaListener(topics = "delete-book-topic", groupId = "libraryservice-group")
        public void handleDeleteBook(Long bookId) {
           // log.info("Received message from Kafka with book ID to delete: " + bookId);
            try {
                BookInfo bookInfo = bookInfoRepository.findByBookId(bookId)
                        .orElseThrow(() -> new RuntimeException("Book not found"));
             bookInfoRepository.delete(bookInfo);
               // log.info("Soft deleted BookInfo with book ID: " + bookId);
            } catch (Exception e) {
                //log.error("Failed to soft delete BookInfo: " + e.getMessage(), e);
            }
        }

}




