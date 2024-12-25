package service;

import com.example.library.service.BookProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
public class BookProducerTest {

    @Mock
    private KafkaTemplate<String, Long> kafkaTemplate;

    @InjectMocks
    private BookProducer bookProducer;

    @Test
    void testSendBookEvent() {
         // Подготовка мока результата отправки
        CompletableFuture<SendResult<String, Long>> future = CompletableFuture.completedFuture(new SendResult<>(new ProducerRecord<>("new-book-topic", 1L), new RecordMetadata(new TopicPartition("new-book-topic", 0), 0, 0, 0L, Long.valueOf(0), 0, 0)));
        when(kafkaTemplate.send(anyString(), anyLong())).thenReturn(future);
        // Вызов метода отправки сообщения
        bookProducer.sendBookEvent("new-book-topic", 1L);
        // Проверка, что метод send был вызван
        verify(kafkaTemplate, times(1)).send("new-book-topic", 1L);
    }
}
