package com.loginregister.template.rest;


import com.loginregister.template.dto.*;
import org.springframework.web.bind.annotation.*;
import com.loginregister.template.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public RegisterResponseTO register(@RequestBody RegisterRequestTO request) {
        return service.register(request);
    }

    @GetMapping("/activate")
    public String activateAccount(@RequestParam String token) {
        return service.activateAccount(token);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponseTO authenticate(@RequestBody AuthenticationRequestTO request) {
        return service.authenticate(request);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody PasswordResetEmailRequestTO request) {
        return service.sendResetPasswordEmail(request.getEmail());
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequestTO request) {
        return service.resetPassword(request);
    }

}
