package com.example.library.service;


import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;

import static org.mockito.ArgumentMatchers.eq;


import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;


@ExtendWith(MockitoExtension.class)
@DisplayName("Test for BookProducer")
public class BookProducerTest {

    @InjectMocks
    private BookProducer bookProducer;

    @Mock
    private KafkaTemplate<String, Long> kafkaTemplate;

    @Test
    @DisplayName("Should send book event")
    void shouldSendBookEvent() {
        String topic = "test-topic";
        Long bookId = 1L;
        CompletableFuture<SendResult<String, Long>> future = CompletableFuture.completedFuture(mock(SendResult.class));

        when(kafkaTemplate.send(eq(topic), eq(bookId))).thenReturn(future);

        bookProducer.sendBookEvent(topic, bookId);

        verify(kafkaTemplate).send(topic, bookId);
    }
}


