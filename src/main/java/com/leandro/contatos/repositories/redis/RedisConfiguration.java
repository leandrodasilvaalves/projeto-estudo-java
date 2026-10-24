package com.leandro.contatos.repositories.redis;

import java.time.Duration;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
    private final CacheProperties cacheProperties;

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        var configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(cacheProperties.getHost());
        if (cacheProperties.getPort() != null) {
            configuration.setPort(cacheProperties.getPort());
        }
        if (StringUtils.hasText(cacheProperties.getUsername())) {
            configuration.setUsername(cacheProperties.getUsername());
        }
        if (StringUtils.hasText(cacheProperties.getPassword())) {
            configuration.setPassword(cacheProperties.getPassword());
        }
        return configuration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Configuracao padrao: vale para qualquer cache nao listado explicitamente abaixo.
        var defaultConfig = cacheConfig(Duration.ofMinutes(10));

        // TTL individual por nome de cache (o valor de cacheNames no @Cacheable).
        Map<String, RedisCacheConfiguration> perCacheConfigs = Map.of(
                "contacts", cacheConfig(Duration.ofMinutes(5)),   // listagem: expira mais rapido
                "contact", cacheConfig(Duration.ofMinutes(30))    // item individual: vive mais
        );

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(perCacheConfigs)
                .build();
    }

    private RedisCacheConfiguration cacheConfig(Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(ttl)
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(RedisSerializer.json()));
    }
}
