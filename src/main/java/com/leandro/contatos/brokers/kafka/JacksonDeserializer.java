package com.leandro.contatos.brokers.kafka;

import org.apache.kafka.common.serialization.Deserializer;

import tools.jackson.databind.ObjectMapper;

/**
 * Deserializer Kafka baseado no Jackson 3 (tools.jackson), compativel com o
 * Spring Boot 4. Converte o JSON (bytes) da mensagem no tipo alvo.
 */
public class JacksonDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> targetType;

    public JacksonDeserializer(ObjectMapper objectMapper, Class<T> targetType) {
        this.objectMapper = objectMapper;
        this.targetType = targetType;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        return objectMapper.readValue(data, targetType);
    }
}
