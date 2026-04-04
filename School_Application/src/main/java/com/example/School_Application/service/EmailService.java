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
   public void sendApprovalEmail(String toEmail, String studentName,
                               String applicationNumber, String grade) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject("AMJS School - Application Approved - " + applicationNumber);
    message.setText(
        "Dear Parent,\n\n" +
        "We are pleased to inform you that the application for " +
        studentName + " has been APPROVED.\n\n" +
        "Application Details:\n" +
        "Student Name: " + studentName + "\n" +
        "Grade Applied: " + grade + "\n" +
        "Application Number: " + applicationNumber + "\n\n" +
        "Please visit the school at your earliest convenience to " +
        "discuss admission formalities and fee details.\n\n" +
        "School Hours: Monday to Friday, 9:00 AM to 4:00 PM\n\n" +
        "Congratulations!\n\n" +
        "Regards,\nAMJS School Admissions Team"
    );
    mailSender.send(message);
}

public void sendRejectionEmail(String toEmail, String studentName,
                                String applicationNumber, String grade,
                                String reason) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject("AMJS School - Application Status - " + applicationNumber);
    message.setText(
        "Dear Parent,\n\n" +
        "We regret to inform you that the application for " +
        studentName + " has not been approved at this time.\n\n" +
        "Application Details:\n" +
        "Student Name: " + studentName + "\n" +
        "Grade Applied: " + grade + "\n" +
        "Application Number: " + applicationNumber + "\n\n" +
        "Reason: " + reason + "\n\n" +
        "You are welcome to reapply in the next admission cycle.\n\n" +
        "Regards,\nAMJS School Admissions Team"
    );
    mailSender.send(message);
}



}
