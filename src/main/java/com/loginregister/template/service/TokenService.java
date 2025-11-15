package com.loginregister.template.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {

    public String generateActivationToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public boolean isTokenExpired(LocalDateTime expiryDate) {
        return expiryDate == null || expiryDate.isBefore(LocalDateTime.now());

    }
}
