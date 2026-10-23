package com.leandro.contatos.brokers.kafka.producers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.leandro.contatos.entities.Contact;

import tools.jackson.databind.ObjectMapper;

@Component ("kafkaContactProducer")
public class ContactProducer implements KafkaProducer {

    @Value("${topics.contacts}")
    private String topic;

    private ObjectMapper objectMapper;

    private KafkaTemplate<String, String> kafkaTemplate;

    public ContactProducer(
            ObjectMapper objectMapper,
            KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String sendMessage(Contact contact) {
        var message = objectMapper.writeValueAsString(contact);
        kafkaTemplate.send(topic, message);
        return "Message sent";
    }
}