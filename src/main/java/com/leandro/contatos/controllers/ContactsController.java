package com.leandro.contatos.controllers;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.leandro.contatos.DTOs.ContactDto;
import com.leandro.contatos.brokers.kafka.producers.KafkaProducer;
import com.leandro.contatos.brokers.rabbit.producers.RabbitProducer;
import com.leandro.contatos.entities.Contact;
import com.leandro.contatos.repositories.mongodb.ContactMongoRepository;
import com.leandro.contatos.repositories.mysql.ContactRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

	private ContactRepository repository;
	private RabbitProducer rabbit;
	private KafkaProducer kafka;
	private ContactMongoRepository mongoRepository;

	public ContactsController(
			ContactRepository repository,
			RabbitProducer rabbit,
			KafkaProducer kafka,
			ContactMongoRepository mongoRepository) {
		this.repository = repository;
		this.rabbit = rabbit;
		this.kafka = kafka;
		this.mongoRepository = mongoRepository;
	}

	@GetMapping
	@Cacheable(cacheNames = "contacts", key = "'find:all'")
	public List<Contact> findAll() {
		return repository.findAll();
	}

	@GetMapping("/mongo")
	public List<ContactDto> findAllMongo() {
		return mongoRepository.findAll();
	}

	@GetMapping("/{id}")
	@Cacheable(cacheNames = "contacts", key = "'find:id:' + #id", unless = "#result == null")
	public Contact findById(@PathVariable long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/mongo/{id}")
	public ResponseEntity<ContactDto> findByIdMongo(@PathVariable String id) {
		return mongoRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@CacheEvict(cacheNames = "contacts", allEntries = true)
	public Contact create(@RequestBody Contact entity) {
		var saved = repository.save(entity);
		mongoRepository.insert(ContactDto.create(saved));

		rabbit.sendMessage(saved);
		kafka.sendMessage(saved);
		return saved;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Contact> update(
			@PathVariable long id, @RequestBody Contact entity) {

		return repository.findById(id)
				.map(record -> {
					record.setName(entity.getName());
					record.setEmail(entity.getEmail());
					record.setPhone(entity.getPhone());

					Contact updated = repository.save(record);
					return ResponseEntity.ok().body(updated);
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		return repository.findById(id)
				.map(record -> {
					repository.deleteById(id);
					return ResponseEntity.ok().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
