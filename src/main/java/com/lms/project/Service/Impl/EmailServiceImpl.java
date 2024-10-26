package com.lms.project.Service.Impl;

import com.lms.project.Service.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String[] to, String subject, String text) {
        if (to == null || to.length == 0 || !areValidEmails(to)) {
            throw new IllegalArgumentException("Invalid email address");
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private boolean areValidEmails(String[] emails) {
        for (String email : emails) {
            if (email == null || !email.contains("@")) {
                return false;
            }
        }
        return true;
    }

}
