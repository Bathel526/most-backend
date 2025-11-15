package com.loginregister.template.persistence.dao.impl;


import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import com.loginregister.template.persistence.dao.UserDao;
import com.loginregister.template.persistence.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class UserDaoImpl extends AbstractDao<UserEntity, Long> implements UserDao {
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try{
            UserEntity userEntity = entityManager
                    .createNamedQuery("UserEntity.findByEmail", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.ofNullable(userEntity);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByActivationToken(String activationToken) {
        try {
            UserEntity userEntity = entityManager
                    .createNamedQuery("UserEntity.findByActivationToken", UserEntity.class)
                    .setParameter("activationToken", activationToken)
                    .getSingleResult();
            return Optional.ofNullable(userEntity);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public int deleteAllByEnabledFalseAndTokenExpiryBefore(LocalDateTime expiry) {
        return entityManager
                .createNamedQuery("UserEntity.deleteAllByEnabledFalseAndTokenExpiryBefore")
                .setParameter("expiry", expiry)
                .executeUpdate();
    }

    @Override
    public Optional<UserEntity> findByResetPasswordToken(String resetPasswordToken) {

        try {
            UserEntity userEntity = entityManager
                    .createNamedQuery("UserEntity.findByResetPasswordToken", UserEntity.class)
                    .setParameter("resetPasswordToken", resetPasswordToken)
                    .getSingleResult();
            return Optional.ofNullable(userEntity);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
