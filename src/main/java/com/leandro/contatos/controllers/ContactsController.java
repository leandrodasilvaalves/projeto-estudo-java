package com.leandro.contatos.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leandro.contatos.brokers.rabbit.producers.ContactProducer;
import com.leandro.contatos.entities.Contact;
import com.leandro.contatos.repositories.ContactRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

	private ContactRepository repository;
	private ContactProducer producer;

	public ContactsController(ContactRepository repository, ContactProducer producer) {
		this.repository = repository;
		this.producer = producer;
	}

	@GetMapping
	public List<Contact> findAll() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Contact> findById(@PathVariable long id) {
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Contact create(@RequestBody Contact entity) {
		// return repository.save(entity);
		var saved = repository.save(entity);
		producer.sendMessage(saved.toString());
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
