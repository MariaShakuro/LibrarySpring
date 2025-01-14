package com.example.library.service;
/*
import com.example.library.BaseTest;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.springframework.kafka.support.SendResult;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;


@DisplayName("Unit Tests for BookProducer")
public class BookProducerTest extends BaseTest {

    @InjectMocks
    private BookProducer bookProducer;

    @Test
    @DisplayName("Should Send Book Event to Kafka")
    void shouldSendBookEvent() {
        // Arrange
        String topic = "test-topic";
        Long bookId = 1L;
        CompletableFuture<SendResult<String, Long>> future = CompletableFuture.completedFuture(mock(SendResult.class));
        when(kafkaTemplate.send(eq(topic), eq(bookId))).thenReturn(future);

        bookProducer.sendBookEvent(topic, bookId);

        verify(kafkaTemplate, times(1)).send(topicCaptor.capture(), bookIdCaptor.capture());
        assertEquals(topic, topicCaptor.getValue());
        assertEquals(bookId, bookIdCaptor.getValue());
    }
}
*/