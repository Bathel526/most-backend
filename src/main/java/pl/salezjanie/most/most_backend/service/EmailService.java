package pl.salezjanie.most.most_backend.service;


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
        message.setSubject("Activation link");
        message.setText("Activation Link: " + activationLink +
                "\n\n" + activationToken);

        mailSender.send(message);
    }

}
