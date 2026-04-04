package com.example.School_Application.controller;

import com.example.School_Application.dto.AdminLoginRequest;
import com.example.School_Application.dto.RejectRequest;
import com.example.School_Application.entity.Application;
import com.example.School_Application.service.AdminService;
import com.example.School_Application.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request) {
        try {
            String token = adminService.login(
                request.getEmail(), request.getPassword());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/applications")
    public ResponseEntity<?> getAllApplications(HttpServletRequest request) {
        try {
            validateAdminToken(request);
            List<Application> applications = adminService.getAllApplications();
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            log.error("Failed to fetch applications: {}", e.getMessage(), e);
            return ResponseEntity.status(500)
                .body(Map.of("error", e.getMessage())); // temporary to see real error
        }
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<?> getApplication(
            @PathVariable String id,
            HttpServletRequest request) {
        try {
            validateAdminToken(request);
            Application application = adminService.getApplicationById(id);
            return ResponseEntity.ok(application);
        } catch (Exception e) {
            log.error("Failed to fetch application", e);
            return ResponseEntity.status(500)
                .body(Map.of("error", "An error occurred."));
        }
    }

    @PostMapping("/applications/{id}/approve")
    public ResponseEntity<?> approveApplication(
            @PathVariable String id,
            HttpServletRequest request) {
        try {
            validateAdminToken(request);
            Application application = adminService.approveApplication(id);
            return ResponseEntity.ok(Map.of(
                "message", "Application approved successfully",
                "applicationNumber", application.getApplicationNumber()
            ));
        } catch (Exception e) {
            log.error("Failed to approve application", e);
            return ResponseEntity.status(500)
                .body(Map.of("error", "An error occurred."));
        }
    }

    @PostMapping("/applications/{id}/reject")
    public ResponseEntity<?> rejectApplication(
            @PathVariable String id,
            @RequestBody RejectRequest rejectRequest,
            HttpServletRequest request) {
        try {
            validateAdminToken(request);
            Application application = adminService.rejectApplication(
                id, rejectRequest.getReason());
            return ResponseEntity.ok(Map.of(
                "message", "Application rejected",
                "applicationNumber", application.getApplicationNumber()
            ));
        } catch (Exception e) {
            log.error("Failed to reject application", e);
            return ResponseEntity.status(500)
                .body(Map.of("error", "An error occurred."));
        }
    }

    private void validateAdminToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        throw new RuntimeException("No authorization header");
    }
    String token = authHeader.substring(7);
    try {
        String role = jwtService.extractRole(token);
        System.out.println("=== ADMIN ROLE FROM TOKEN: " + role + " ===");
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Unauthorized");
        }
    } catch (RuntimeException e) {
        throw e;
    } catch (Exception e) {
        throw new RuntimeException("Unauthorized");
    }
}
}