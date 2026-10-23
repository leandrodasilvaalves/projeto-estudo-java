package com.leandro.contatos.brokers.kafka.producers;

import com.leandro.contatos.entities.Contact;

public interface KafkaProducer {
    public String sendMessage(Contact contact);
}