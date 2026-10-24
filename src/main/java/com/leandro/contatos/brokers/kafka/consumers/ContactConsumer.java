package com.leandro.contatos.brokers.kafka.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.leandro.contatos.entities.Contact;

@Component("kafkaContactConsumer")
public class ContactConsumer {

    @KafkaListener(topics = "${topics.contacts}", groupId = "contacts-1")
    public void consumeMessage(Contact message) {
        System.out.println("[Kafka] Received message: " + message);
    }
}
