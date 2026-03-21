package com.example.School_Application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ApplicationRequest {

    @NotBlank(message = "Grade is required")
    private String grade;

    @NotBlank(message = "Student name is required")
    private String studentName;

    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Aadhaar number is required")
    @Pattern(regexp = "^[2-9][0-9]{11}$", message = "Invalid Aadhaar number")
    private String aadhaarNumber;

    private String bloodGroup;
    private String motherTongue;
    private String nationality;

    @NotBlank(message = "Father name is required")
    private String fatherName;

    private String fatherOccupation;

    @NotBlank(message = "Mother name is required")
    private String motherName;

    private String motherOccupation;

    @NotBlank(message = "Primary contact is required")
    private String primaryContact;

    private String secondaryContact;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String currentAddress;
    private String city;
    private String district;
    private String pincode;
    private String previousSchoolName;
    private String previousSchoolAddress;
    private String previousGradeCompleted;
    private String yearOfPassing;
    private String tcNumber;
    private String caste;
    private String religion;

    @Size(max = 2_000_000, message = "Photo too large (max ~1.5MB)")
    private String studentPhoto;
}