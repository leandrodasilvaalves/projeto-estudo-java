package com.leandro.contatos.brokers.rabbit.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.leandro.contatos.entities.Contact;

@Component("rabbitContactProducer")
public class ContactProducer implements RabbitProducer {

    private RabbitTemplate rabbitTemplate;

    public ContactProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Contact message) {
        rabbitTemplate.convertAndSend(
            "exchange-name", "", message);
    }
}
