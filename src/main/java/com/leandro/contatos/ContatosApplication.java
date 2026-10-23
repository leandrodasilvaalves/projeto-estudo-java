package com.leandro.contatos;

import java.util.stream.LongStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.leandro.contatos.entities.Contact;
import com.leandro.contatos.repositories.ContactRepository;

@SpringBootApplication
public class ContatosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContatosApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ContactRepository repository) {
		return args -> {
			if (repository.findAll().size() <= 0) {
				// repository.deleteAll();
				LongStream.range(1, 11)
						.mapToObj(i -> {
							var c = new Contact();
							c.setName("Contact " + i);
							c.setEmail("contact" + i + "@email.com");
							c.setPhone("(84) 9999-999" + i);
							return c;
						})
						.map(v -> repository.save(v))
						.forEach(System.out::println);
				;
			}
		};
	}
}
