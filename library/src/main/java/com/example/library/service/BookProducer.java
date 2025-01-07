package com.example.library.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class BookProducer {

    private final KafkaTemplate<String, Long> kafkaTemplate;

    @Autowired
    public BookProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookEvent(String topic,Long bookId) {
        log.info("Sent message to Kafka with book ID: " + bookId);
        kafkaTemplate.send(topic, bookId).whenComplete((result,ex)->{}
        );
    }
}

