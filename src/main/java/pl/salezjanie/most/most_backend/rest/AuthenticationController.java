package pl.salezjanie.most.most_backend.rest;


import org.springframework.web.bind.annotation.*;
import pl.salezjanie.most.most_backend.dto.AuthenticationRequestTO;
import pl.salezjanie.most.most_backend.dto.AuthenticationResponseTO;
import pl.salezjanie.most.most_backend.dto.RegisterRequestTO;
import pl.salezjanie.most.most_backend.dto.RegisterResponseTO;
import pl.salezjanie.most.most_backend.service.AuthenticationService;

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

}
