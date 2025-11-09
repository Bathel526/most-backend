package pl.salezjanie.most.most_backend.persistence.dao;

import pl.salezjanie.most.most_backend.persistence.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserDao extends Dao<UserEntity, Long> {

    Optional<UserEntity> findByEmail (String email);

    Optional<UserEntity> findByActivationToken(String activationToken);

    int deleteAllByEnabledFalseAndTokenExpiryBefore(LocalDateTime expiry);


}
