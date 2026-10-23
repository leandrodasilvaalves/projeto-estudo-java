package com.leandro.contatos.brokers.kafka.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component("kafkaContactConsumer")
public class ContactConsumer {

    @KafkaListener(topics = "${topics.contacts}", groupId = "contacts-1")
    public void consumeMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
