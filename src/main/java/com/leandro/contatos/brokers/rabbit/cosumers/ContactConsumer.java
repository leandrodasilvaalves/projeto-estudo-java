package com.leandro.contatos.brokers.rabbit.cosumers;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class ContactConsumer {

	@RabbitListener(queues = "queue-name")
	public void receiveMessage(String message) {
		System.out.println("Received message: " + message);
	}
}
