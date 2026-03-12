package com.example.School_Application.service;

import com.example.School_Application.dto.RegisterRequest;
import com.example.School_Application.entity.UserEntity;
import com.example.School_Application.repository.UserRepository;
import com.example.School_Application.dto.LoginRequest;
import com.example.School_Application.exception.DuplicateEmailException;
import com.example.School_Application.exception.InvalidCredentialsException;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .enabled(true)
                .createdAt(LocalDate.now())
                .build();
        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return jwtService.generateToken(user.getEmail(), user.getRole());
    }
}
