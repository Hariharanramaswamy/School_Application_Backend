package com.example.School_Application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

   public void sendApplicationConfirmation(String toEmail, String studentName,String applicationNumber, String grade){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("AMJS School - Application Received - " + applicationNumber);
        message.setText(
            "Dear Parent,\n\n" +
            "Thank you for applying to AMJS School.\n\n" +
            "Application Details:\n" +
            "Student Name: " + studentName + "\n" +
            "Grade Applied: " + grade + "\n" +
            "Application Number: " + applicationNumber + "\n" +
            "Status: PENDING\n\n" +
            "We will review your application and contact you shortly.\n\n" +
            "Regards,\nAMJS School Admissions Team"
        );
        mailSender.send(message);
   }



}
