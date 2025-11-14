package pl.salezjanie.most.most_backend.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/demo-controller")
@CrossOrigin(origins = "*")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> seyHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
