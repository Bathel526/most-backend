package com.loginregister.template.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
