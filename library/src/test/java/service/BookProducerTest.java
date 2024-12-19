package service;

import com.example.library.service.BookProducer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
public class BookProducerTest {

    @Mock
    private KafkaTemplate<String, Long> kafkaTemplate;

    @InjectMocks
    private BookProducer bookProducer;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testSendBookEvent() {
        Long bookId = 1L;
        String topic = "test-topic";

        bookProducer.sendBookEvent(topic, bookId);

        verify(kafkaTemplate, times(1)).send(topic, bookId);
    }
}
