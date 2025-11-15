package com.loginregister.template.service;


import com.loginregister.template.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.loginregister.template.persistence.dao.UserDao;
import com.loginregister.template.persistence.entity.UserEntity;
import com.loginregister.template.persistence.enums.Role;
import com.loginregister.template.security.service.JwtService;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final TokenService tokenService;

    public RegisterResponseTO register(RegisterRequestTO request) {
        // is user present in database
        if (userDao.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Użytkownik z tym emailem już istnieje");
        }

        // Generating activation token
        String activationToken = tokenService.generateActivationToken();

        // Creating unactive UserEntity
        var user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(false) // unactive user
                .activationToken(activationToken)
                .tokenExpiry(LocalDateTime.now().plusHours(24)) // token will be expired after 24 h
                .build();

        userDao.save(user);

        // activation mail
        emailService.sendActivationEmail(user.getEmail(), activationToken);

        return RegisterResponseTO.builder()
                .message("Registration successful!")
                .success(String.valueOf(true))
                .build();
    }

    public String activateAccount(String token) {

        System.out.println("Received token: '" + token + "'");

        Optional<UserEntity> userOpt = userDao.findByActivationToken(token);

        if(userOpt.isPresent()) {
            System.out.println("User found: " + userOpt.get());
        } else {
            System.out.println("No user found for token!");
        }




        // Check if token exists in database
        if (userOpt.isEmpty()) {
            return "Invalid or already used activation token.";
        }

        UserEntity user = userOpt.get();

        // First check if account is already active
        if (user.isEnabled()) {
            return "Account has already been activated previously! You can log in.";
        }

        // Then check if token has expired
        if (tokenService.isTokenExpired(user.getTokenExpiry())) {
            return "Activation token has expired. Please register again.";
        }

        // Activate the account
        user.setEnabled(true);
        user.setActivationToken(null);  // Remove used token
        user.setTokenExpiry(null);      // Remove expiry date
        userDao.save(user);

        return "Account has been successfully activated! You can now log in.";
    }
    public AuthenticationResponseTO authenticate(AuthenticationRequestTO request) {
        // is user active
        var user = userDao.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Konto nieaktywne. Sprawdź email w celu aktywacji.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseTO.builder()
                .token(jwtToken)
                .build();
    }

    public String sendResetPasswordEmail(String email) {
        Optional<UserEntity> userOpt = userDao.findByEmail(email);

        if(userOpt.isEmpty()) {
            return "Użytkownik nie istnieje";
        }

        UserEntity user = userOpt.get();

        String resetToken = tokenService.generateActivationToken();
        user.setResetPasswordToken(resetToken);
        user.setTokenExpiry(LocalDateTime.now().plusHours(2));
        userDao.save(user);

        emailService.sendResetPasswordEmail(user.getEmail(), resetToken);

        return "Link do resetu hasła został wysłany na Twój email.";

    }

    public String resetPassword(PasswordResetRequestTO request) {
        Optional<UserEntity> userOpt = userDao.findByResetPasswordToken(request.getToken());

        if(userOpt.isEmpty()) {
            return "Nieprwaidłowy lub wygałsy token";
        }

        UserEntity user = userOpt.get();

        if (tokenService.isTokenExpired(user.getTokenExpiry())) {
            return "Token wygasł. Wygeneruj nowy link resetujący hasło.";
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetPasswordToken(null);
        user.setTokenExpiry(null);
        userDao.save(user);
        return "Hasło zostało zmienione pomyślnie";
    }
}
