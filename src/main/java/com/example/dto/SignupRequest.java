package com.example.dto;

import com.example.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String nom;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String motDePasse;

    private Role role;
}
