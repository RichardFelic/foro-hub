package com.hub.foro.api.modules.user.dto;

import com.hub.foro.api.modules.shared.validation.Unique;
import com.hub.foro.api.modules.user.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DtoUserRegistration(
    Long id,
    @NotBlank(message = "Username cannot be blank or null")
    @Unique(entity = User.class, field = "username", message = "El nombre de usuario ya está registrado.")
    String username,
    @NotBlank(message = "Email cannot be blank or null")
    @Unique(entity = User.class, field = "email", message = "El correo ya está registrado.")
    @Email(message = "Invalid email format")
    String email,
    @NotBlank(message = "Password cannot be blank or null")
    String password,
    @NotBlank(message = "Role cannot be blank or null")
    String role,
    Boolean enabled
) {

    public DtoUserRegistration(User savedUser) {
        this(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword(), savedUser.getRole(), savedUser.getEnabled());
    }

}
