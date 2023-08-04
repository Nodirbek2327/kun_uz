package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    //    @NotNull(message = "Name is required")
//    @Size(min = 3, message = "Name should be at least 3 characters")
//    @NotEmpty(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "surname is required")
    private String surname;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
}