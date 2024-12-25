package libraryservice.libraryservice.service;

import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
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
    private static final Logger logger = LoggerFactory.getLogger(BookConsumer.class);

    @Autowired
    public BookConsumer(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }

    @KafkaListener(topics = "new-book-topic", groupId = "libraryservice-group")
    public void handleNewBook(Long bookId) {
        logger.info("Received message from Kafka with book ID: " + bookId);
        try {
            Optional<BookInfo> existingBookInfo = bookInfoRepository.findByBookId(bookId);
            if (existingBookInfo.isPresent()) {
                logger.warn("BookInfo with book ID " + bookId + " already exists, updating status.");
                BookInfo bookInfo = existingBookInfo.get();
                bookInfo.setStatus("available");
                bookInfo.setBorrowTime(LocalDateTime.now());
                bookInfo.setReturnTime(LocalDateTime.now().plusWeeks(2));
                bookInfoRepository.save(bookInfo);
            } else {
                BookInfo newBookInfo = new BookInfo();
                newBookInfo.setBookId(bookId); // Устанавливаем bookId как bookId
                newBookInfo.setStatus("available");
                newBookInfo.setBorrowTime(LocalDateTime.now());
                newBookInfo.setReturnTime(LocalDateTime.now().plusWeeks(2));
                bookInfoRepository.save(newBookInfo);
            }
            logger.info("Saved BookInfo with book ID: " + bookId);
        } catch (Exception e) {
            logger.error("Failed to save BookInfo: " + e.getMessage(), e);
        }
    }

        @KafkaListener(topics = "delete-book-topic", groupId = "libraryservice-group")
        public void handleDeleteBook(Long bookId) {
            logger.info("Received message from Kafka with book ID to delete: " + bookId);
            try {
                BookInfo bookInfo = bookInfoRepository.findByBookId(bookId)
                        .orElseThrow(() -> new RuntimeException("Book not found"));
                bookInfo.setIsDeleted(true); // Мягкое удаление
                bookInfoRepository.save(bookInfo);
                logger.info("Soft deleted BookInfo with book ID: " + bookId);
            } catch (Exception e) {
                logger.error("Failed to soft delete BookInfo: " + e.getMessage(), e);
            }
        }

}




