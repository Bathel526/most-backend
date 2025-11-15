package com.loginregister.template.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.loginregister.template.persistence.dao.UserDao;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserCleanUpService {
    private final UserDao userDao;

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void cleanupExpiredActivations() {
        int deletedCount = userDao.deleteAllByEnabledFalseAndTokenExpiryBefore(LocalDateTime.now());
        System.out.println("Deleted " + deletedCount + " unactive users");
    }
}
