package com.leandro.contatos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandro.contatos.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {} 