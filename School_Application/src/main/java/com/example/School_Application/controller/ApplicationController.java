package com.example.School_Application.controller;

import com.example.School_Application.dto.ApplicationRequest;
import com.example.School_Application.entity.Application;
import com.example.School_Application.service.ApplicationService;
import com.example.School_Application.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApplicationController {

    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    private final ApplicationService applicationservice;
    private final JwtService jwtservice;

    @GetMapping("my-application")
    public ResponseEntity<?> getMyApplication(HttpServletRequest httpRequest) {
        try {
            String token = httpRequest.getHeader("Authorization").substring(7);
            String userId = jwtservice.extractEmail(token);
            Application application = applicationservice.getApplicationByUserId(userId);
            if (application == null) {
                return ResponseEntity.status(404).body(Map.of("error", "No Application Found"));
            }
            return ResponseEntity.ok(application);
        } catch (Exception e) {
            log.error("Request failed", e);
            return ResponseEntity.status(500).body(Map.of("error", "An error occurred."));
        }
    }

    @PostMapping("/admission")
    public ResponseEntity<?> submitApplication(@Valid @RequestBody ApplicationRequest request,
                                               HttpServletRequest httpRequest) {
        try {
            String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401)
                        .body(Map.of("error", "No authorization header"));
            }
            String token = authHeader.substring(7);
            String userId = jwtservice.extractEmail(token);

            String applicationNumber = applicationservice.submitApplication(request, userId);
            return ResponseEntity.ok(Map.of(
                    "message", "Application submitted successfully",
                    "applicationNumber", applicationNumber
            ));
        } catch (Exception e) {
            log.error("Request failed", e);
            return ResponseEntity.status(500).body(Map.of("error", "An error occurred."));
        }
    }

    @PutMapping("/my-application")
    public ResponseEntity<?> updateMyApplication(@Valid @RequestBody ApplicationRequest request,
                                                 HttpServletRequest httpRequest) {
        try {
            String token = httpRequest.getHeader("Authorization").substring(7);
            String userId = jwtservice.extractEmail(token);
            Application updated = applicationservice.updateApplication(userId, request);
            return ResponseEntity.ok(Map.of(
                    "message", "Application updated successfully",
                    "applicationNumber", updated.getApplicationNumber()
            ));
        } catch (Exception e) {
            log.error("Request failed", e);
            return ResponseEntity.status(500).body(Map.of("error", "An error occurred."));
        }
    }
}
