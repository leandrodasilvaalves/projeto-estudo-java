package com.leandro.contatos.brokers.rabbit.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContactProducer {

    private RabbitTemplate rabbitTemplate;

    public ContactProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(
            "exchange-name", "", message);
    }
}
