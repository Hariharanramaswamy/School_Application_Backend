package com.example.School_Application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "admin")
public class AdminProperties {

    private String email;
    private String password;
}
