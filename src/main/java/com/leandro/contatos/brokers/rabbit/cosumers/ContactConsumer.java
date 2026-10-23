package com.leandro.contatos.brokers.rabbit.cosumers;

import org.springframework.stereotype.Component;

import com.leandro.contatos.entities.Contact;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component("rabbitContactConsumer")
public class ContactConsumer {

	@RabbitListener(queues = "queue-name")
	public void receiveMessage(Contact message) {
		System.out.println("Received message: " + message);
	}
}
