package com.example.School_Application.entity;

import lombok.*;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String role;

    private LocalDate createdAt;
    private boolean enabled;
}
