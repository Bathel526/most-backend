package com.loginregister.template.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendActivationEmail(String toEmail, String activationToken) {
        String activationLink = "http://localhost:8080/api/v1/auth/activate?token=" + activationToken;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Link aktywacyjny");
        message.setText("Link aktywacyjny do twojego konta: " + activationLink);

        mailSender.send(message);
    }

    @Async
    public void sendResetPasswordEmail(String email, String resetToken) {
        String resetLink = "http://localhost:8080/api/v1/auth/reset-password?token=" + resetToken;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset hasła");
        message.setText("Link do resetu hasła: " + resetLink);

        mailSender.send(message);
    }
}
