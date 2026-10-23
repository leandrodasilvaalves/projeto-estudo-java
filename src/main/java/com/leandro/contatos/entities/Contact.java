package com.leandro.contatos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @Override
    public String toString() {
        return "{" +
                "\"id\": \"" + id + "\", " +
                "\"name\": \"" + name + "\", " +
                "\"email\": \"" + email + "\", " +
                "\"phone\": \"" + phone + "\" " +
                "}";
    }
}
