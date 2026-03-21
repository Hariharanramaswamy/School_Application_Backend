package com.example.School_Application.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "Application")
public class Application {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed(unique = true)
    private String applicationNumber;

    private String grade;
    private String status;

    // Student details
    private String studentName;
    private String gender;
    private String dateOfBirth;
    private String bloodGroup;

    @Indexed
    private String aadhaarNumber;

    private String motherTongue;
    private String nationality;
    private String caste;
    private String religion;
    private String studentPhoto;

    // Parent Info
    private String fatherName;
    private String fatherOccupation;
    private String motherName;
    private String motherOccupation;
    private String primaryContact;
    private String secondaryContact;
    private String email;

    // Address
    private String currentAddress;
    private String city;
    private String district;
    private String pincode;

    // Academic Info
    private String previousSchoolName;
    private String previousSchoolAddress;
    private String previousGradeCompleted;
    private String yearOfPassing;
    private String tcNumber;

    private LocalDateTime createdAt;
}
