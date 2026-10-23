package com.leandro.contatos.repositories.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandro.contatos.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {} 