package com.loginregister.template.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.loginregister.template.persistence.enums.Role;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@NamedQueries({
        @NamedQuery(
                name = "UserEntity.findByEmail",
                query = "SELECT u FROM UserEntity u WHERE u.email = :email"
        ),
        @NamedQuery(
                name = "UserEntity.findByActivationToken",
                query = "SELECT u FROM UserEntity u WHERE u.activationToken = :activationToken"
        ),
        @NamedQuery(
                name = "UserEntity.deleteAllByEnabledFalseAndTokenExpiryBefore",
                query = "DELETE FROM UserEntity u WHERE u.enabled = false AND u.tokenExpiry < :expiry"
        ),
        @NamedQuery(
                name = "UserEntity.findByResetPasswordToken",
                query = "SELECT u FROM UserEntity u WHERE u.resetPasswordToken = :resetPasswordToken"
        )
})


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled;

    private String activationToken;

    private LocalDateTime tokenExpiry;

    private String resetPasswordToken;

    private LocalDateTime resetPasswordExpiry;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
