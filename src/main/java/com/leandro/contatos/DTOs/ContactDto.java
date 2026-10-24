package com.leandro.contatos.DTOs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.leandro.contatos.entities.Contact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "contacts")
public class ContactDto {

    @Id
    private String id;

    private String name;

    private String email;

    private String phone;

    public static ContactDto create(Contact entity) {
        var dto = new ContactDto();

        dto.name = entity.getName();
        dto.email = entity.getEmail();
        dto.phone = entity.getPhone();
        
        return dto;
    }
}
