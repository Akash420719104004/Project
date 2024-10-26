package com.lms.project.controller.HandlerController;
import com.lms.project.Service.Services.EmailService;
import com.lms.project.configure.requestDtos.NotificationRequestDtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
@RestController
public class EmailController {
        @Autowired
        private EmailService emailService;
        @PostMapping("/send-notification")
        public String sendNotification(@RequestBody (required = false)NotificationRequestDtos request) {
            String [] recipients = request.getEmails();
            String subject = "Lunch Plans! \uD83C\uDF7D\uFE0F";
            String body = "Hi everyone!\n" +
                    "\n" +
                    "Hope you’re all doing well! Just wanted to check in and see when we’re planning to go for lunch. Looking forward to catching up and enjoying some good food together!\n" +
                    "\n .";
            emailService.sendEmail(recipients, subject, body);
            return "Email sent successfully!";
        }
    }
