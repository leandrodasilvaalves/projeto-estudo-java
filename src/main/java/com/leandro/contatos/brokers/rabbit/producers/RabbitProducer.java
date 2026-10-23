package com.leandro.contatos.brokers.rabbit.producers;

import com.leandro.contatos.entities.Contact;

public interface RabbitProducer {

    public void sendMessage(Contact message);
}