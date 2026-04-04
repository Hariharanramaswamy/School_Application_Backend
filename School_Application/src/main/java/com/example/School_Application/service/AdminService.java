package com.example.School_Application.service;


import com.example.School_Application.config.AdminProperties;
import com.example.School_Application.repository.ApplicationRepository;
import com.example.School_Application.entity.Application;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminService {
    private static final Logger log=LoggerFactory.getLogger(AdminService.class);
    private final ApplicationRepository applicationRepository;
    private final AdminProperties adminProperties;
    private final JwtService jwtService;
    private final EmailService emailService;
   

    public String login(String email, String password){
        if(!email.equals(adminProperties.getEmail())||
            !password.equals(adminProperties.getPassword())){
                throw new RuntimeException("Invalid admin credentials");
            }
            return jwtService.generateToken(email,"ADMIN");
    }

    public List<Application> getAllApplications(){
        return applicationRepository.findAll();
    }

    public Application getApplicationById(String id){
        return applicationRepository.findById(id).orElseThrow(()->new RuntimeException("Application not found"));
    }

    public Application approveApplication(String id) {
        Application application = getApplicationById(id);
        application.setStatus("APPROVED");
        applicationRepository.save(application);
        try {
            emailService.sendApprovalEmail(
                application.getEmail(),
                application.getStudentName(),
                application.getApplicationNumber(),
                application.getGrade()
            );
        } catch (Exception e) {
            log.warn("Approval email failed: {}", e.getMessage());
        }
        return application;
    }

    public Application rejectApplication(String id, String reason) {
        Application application = getApplicationById(id);
        application.setStatus("REJECTED");
        applicationRepository.save(application);
        try {
            emailService.sendRejectionEmail(
                application.getEmail(),
                application.getStudentName(),
                application.getApplicationNumber(),
                application.getGrade(),
                reason
            );
        } catch (Exception e) {
            log.warn("Rejection email failed: {}", e.getMessage());
        }
        return application;
    }
    }
