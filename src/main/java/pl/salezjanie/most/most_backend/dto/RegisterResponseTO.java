package pl.salezjanie.most.most_backend.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseTO {
    private String message;
    private String success;
}
