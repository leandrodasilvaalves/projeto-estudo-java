package com.leandro.contatos.repositories.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component 
@ConfigurationProperties ("spring.data.redis")
public class CacheProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
}
