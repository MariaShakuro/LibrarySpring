package com.example.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class BookProducer {

    private final KafkaTemplate<String, Long> kafkaTemplate;

    @Autowired
    public BookProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookEvent(String topic, Long bookId) {
        kafkaTemplate.send(topic, bookId).whenComplete((result, ex) -> {
                }
        );
    }
}

