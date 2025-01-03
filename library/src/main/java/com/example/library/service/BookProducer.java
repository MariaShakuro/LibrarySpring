package com.example.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class BookProducer {
    private static final Logger logger= LoggerFactory.getLogger(BookProducer.class);
    private final KafkaTemplate<String, Long> kafkaTemplate;

    @Autowired
    public BookProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookEvent(String topic,Long bookId) {
        logger.info("Sent message to Kafka with book ID: " + bookId);
        kafkaTemplate.send(topic, bookId).whenComplete((result,ex)->{
            if (ex == null) {
                logger.info("Message sent successfully to topic: {}", topic);}
            else { logger.error("Failed to send message to topic: {}", topic, ex); }
                }
        );
    }
}

