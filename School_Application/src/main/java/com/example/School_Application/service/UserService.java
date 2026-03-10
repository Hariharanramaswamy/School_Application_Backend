package com.example.School_Application.service;

import com.example.School_Application.dto.RegisterRequest;
import com.example.School_Application.entity.UserEntity;
import com.example.School_Application.repository.UserRepository;
import com.example.School_Application.dto.LoginRequest;

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
            throw new RuntimeException("Email Already Exists");
        }
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .enabled(true)
                .createdAt(LocalDate.now())
                .build();
        userRepository.save(user);
        return "User Registered Successfully";
    }
    public String login(LoginRequest request){
        UserEntity user= userRepository.findByEmail(request.getEmail())
        .orElseThrow(()->new RuntimeException("Invalid Password or email"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Password or email");
        }
        return jwtService.generateToken(user.getEmail());
    }

}
