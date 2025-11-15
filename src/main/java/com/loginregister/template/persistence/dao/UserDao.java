package com.loginregister.template.persistence.dao;

import com.loginregister.template.persistence.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserDao extends Dao<UserEntity, Long> {

    Optional<UserEntity> findByEmail (String email);

    Optional<UserEntity> findByActivationToken(String activationToken);

    int deleteAllByEnabledFalseAndTokenExpiryBefore(LocalDateTime expiry);

    Optional<UserEntity> findByResetPasswordToken(String resetPasswordToken);

}
