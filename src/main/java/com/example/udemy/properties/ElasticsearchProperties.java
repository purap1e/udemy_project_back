package com.example.udemy.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "elasticsearch")
@Getter
public class ElasticsearchProperties {
    private String host;
    private int port;
    private String username;
    private String password;
}
