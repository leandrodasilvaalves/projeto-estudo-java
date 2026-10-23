package com.leandro.contatos.repositories.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leandro.contatos.DTOs.ContactDto;

@Repository 
public interface ContactMongoRepository 
    extends MongoRepository<ContactDto, String> {

}
