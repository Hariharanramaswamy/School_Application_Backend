package com.example.School_Application.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.School_Application.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
