package com.hub.foro.api.modules.auth.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
// import com.hub.foro.api.user.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DtoRegisterRequest(
        @JsonAlias("username") @NotBlank(message = "Username cannot be blank or null") String username,
        @JsonAlias("email") @NotBlank(message = "Email cannot be blank or null") @Email(message = "Invalid email format") String email,
        @JsonAlias("password") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and contain at least one letter and one number") @NotBlank(message = "Password cannot be blank or null") String password,
        @JsonAlias("role") String role) {

}
