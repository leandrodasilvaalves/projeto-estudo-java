package com.leandro.contatos.DTOs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.leandro.contatos.entities.Contact;

@Document(collection = "contacts")
public class ContactDto {

    public ContactDto(Contact entity) {
        name = entity.getName();
        email = entity.getEmail();
        phone = entity.getPhone();
    }

    @Id
    private String id;

    private String name;

    private String email;

    private String phone;
}
