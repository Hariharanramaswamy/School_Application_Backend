package com.example.School_Application.service;

import com.example.School_Application.dto.ApplicationRequest;
import com.example.School_Application.entity.Application;
import com.example.School_Application.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private static final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationRepository applicationRepository;
    private final EmailService emailService;

    public String submitApplication(ApplicationRequest request, String userId) {
        // Check duplicate application
        if (applicationRepository.existsByAadhaarNumberAndGrade(
                request.getAadhaarNumber(), request.getGrade())) {
            throw new RuntimeException(
                "An application for this student and grade already exists.");
        }

        // Generate application number: AMJS-2026-XXXX
        String applicationNumber = "AMJS-" + Year.now().getValue()
                                   + "-" + generateFourDigitCode();

        Application application = new Application();
        application.setUserId(userId);
        application.setApplicationNumber(applicationNumber);
        application.setGrade(request.getGrade());
        application.setStatus("PENDING");
        application.setStudentName(request.getStudentName());
        application.setDateOfBirth(request.getDateOfBirth());
        application.setGender(request.getGender());
        application.setAadhaarNumber(request.getAadhaarNumber());
        application.setBloodGroup(request.getBloodGroup());
        application.setMotherTongue(request.getMotherTongue());
        application.setNationality(request.getNationality());
        application.setFatherName(request.getFatherName());
        application.setFatherOccupation(request.getFatherOccupation());
        application.setMotherName(request.getMotherName());
        application.setMotherOccupation(request.getMotherOccupation());
        application.setPrimaryContact(request.getPrimaryContact());
        application.setSecondaryContact(request.getSecondaryContact());
        application.setEmail(request.getEmail());
        application.setCurrentAddress(request.getCurrentAddress());
        application.setCity(request.getCity());
        application.setDistrict(request.getDistrict());
        application.setPincode(request.getPincode());
        application.setPreviousSchoolName(request.getPreviousSchoolName());
        application.setPreviousSchoolAddress(request.getPreviousSchoolAddress());
        application.setPreviousGradeCompleted(request.getPreviousGradeCompleted());
        application.setYearOfPassing(request.getYearOfPassing());
        application.setTcNumber(request.getTcNumber());
        application.setStudentPhoto(request.getStudentPhoto());
        application.setCreatedAt(LocalDateTime.now());

        applicationRepository.save(application);

        // Send confirmation email
        try {
            emailService.sendApplicationConfirmation(
                request.getEmail(),
                request.getStudentName(),
                applicationNumber,
                request.getGrade()
            );
        } catch (Exception e) {
            log.warn("Email sending failed for application {}: {}", applicationNumber, e.getMessage());
        }

        return applicationNumber;
    }

    public Application getApplicationByUserId(String userId) {
        List<Application> applications = applicationRepository.findByUserId(userId);
        if (applications.isEmpty()) return null;
        return applications.get(0);
    }

    public Application updateApplication(String userId, ApplicationRequest request) {
        List<Application> applications = applicationRepository.findByUserId(userId);
        if (applications.isEmpty()) {
            throw new RuntimeException("No application found for this user");
        }
        Application application = applications.get(0);
        application.setGrade(request.getGrade());
        application.setStudentName(request.getStudentName());
        application.setDateOfBirth(request.getDateOfBirth());
        application.setGender(request.getGender());
        application.setAadhaarNumber(request.getAadhaarNumber());
        application.setBloodGroup(request.getBloodGroup());
        application.setMotherTongue(request.getMotherTongue());
        application.setNationality(request.getNationality());
        application.setFatherName(request.getFatherName());
        application.setFatherOccupation(request.getFatherOccupation());
        application.setMotherName(request.getMotherName());
        application.setMotherOccupation(request.getMotherOccupation());
        application.setPrimaryContact(request.getPrimaryContact());
        application.setSecondaryContact(request.getSecondaryContact());
        application.setEmail(request.getEmail());
        application.setCurrentAddress(request.getCurrentAddress());
        application.setCity(request.getCity());
        application.setDistrict(request.getDistrict());
        application.setPincode(request.getPincode());
        application.setPreviousSchoolName(request.getPreviousSchoolName());
        application.setPreviousSchoolAddress(request.getPreviousSchoolAddress());
        application.setPreviousGradeCompleted(request.getPreviousGradeCompleted());
        application.setYearOfPassing(request.getYearOfPassing());
        application.setTcNumber(request.getTcNumber());
        application.setStudentPhoto(request.getStudentPhoto());
        return applicationRepository.save(application);
    }

    private String generateFourDigitCode() {
        return String.format("%04d", new Random().nextInt(10000));
    }
}