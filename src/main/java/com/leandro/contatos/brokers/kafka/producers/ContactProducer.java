package com.leandro.contatos.brokers.kafka.producers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.leandro.contatos.entities.Contact;

@Component ("kafkaContactProducer")
public class ContactProducer implements KafkaProducer {

    @Value("${topics.contacts}")
    private String topic;

    private KafkaTemplate<String, Contact> kafkaTemplate;

    public ContactProducer(KafkaTemplate<String, Contact> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(Contact contact) {
        kafkaTemplate.send(topic, contact);
    }
}
