package com.tkmind.ecommerce.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaTestService implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void run(String... args) throws Exception {
        kafkaTemplate.send("my-test-topic", "Hello Kafka from Spring Boot!");
    }

    @KafkaListener(topics = "my-test-topic")
    public void consume(String message) {
        System.out.println("-> Received Message in Console: " + message);
    }

}
