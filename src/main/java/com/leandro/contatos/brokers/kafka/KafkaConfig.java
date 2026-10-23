package com.leandro.contatos.brokers.kafka;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.leandro.contatos.entities.Contact;

import tools.jackson.databind.ObjectMapper;

@Configuration 
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;

    KafkaConfig(KafkaProperties kafkaProperties, ObjectMapper objectMapper) {
        this.kafkaProperties = kafkaProperties;
        this.objectMapper = objectMapper;
    }
    
    @Value("${topics.contacts}")
    private String contactsTopic;

    @Bean
    public ProducerFactory<String, Contact> producerFactory() {
        Map<String, Object> properties = kafkaProperties
                                        .buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(
            properties,
            new StringSerializer(),
            new JacksonSerializer<>(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, Contact> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, Contact> consumerFactory() {
        Map<String, Object> properties = kafkaProperties
                                        .buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(
            properties,
            new StringDeserializer(),
            new JacksonDeserializer<>(objectMapper, Contact.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Contact> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Contact> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public NewTopic contactsTopicBuilder() {
        return TopicBuilder
            .name(contactsTopic)
            .partitions(1)
            .replicas(1)
            .build();
    }
}
