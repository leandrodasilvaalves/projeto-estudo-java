package com.leandro.contatos.brokers.kafka;

import org.apache.kafka.common.serialization.Serializer;

import tools.jackson.databind.ObjectMapper;

/**
 * Serializer Kafka baseado no Jackson 3 (tools.jackson), compativel com o
 * Spring Boot 4. Converte qualquer objeto em JSON (bytes) para a mensagem.
 */
public class JacksonSerializer<T> implements Serializer<T> {

    private final ObjectMapper objectMapper;

    public JacksonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) {
            return null;
        }
        return objectMapper.writeValueAsBytes(data);
    }
}
