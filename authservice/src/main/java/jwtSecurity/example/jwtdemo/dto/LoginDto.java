package jwtSecurity.example.jwtdemo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 6,max = 20)
    private String password;

}
