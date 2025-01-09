package jwtSecurity.example.jwtdemo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    @Min(6)
    @Max(6)
    private String password;


}
