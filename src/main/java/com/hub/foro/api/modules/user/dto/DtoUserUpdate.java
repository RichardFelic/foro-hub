package com.hub.foro.api.modules.user.dto;

import com.hub.foro.api.modules.shared.validation.Unique;
import com.hub.foro.api.modules.user.model.User;

import jakarta.validation.constraints.Email;

public record DtoUserUpdate(
    Long id,
    @Unique(entity = User.class, field = "username", message = "The username already exists")
    String username,
    @Email(message = "Invalid email format")
    @Unique(entity = User.class, field = "email", message = "The email already exists")
    String email,
    String password,
    String role,
    Boolean enabled
) {

    public DtoUserUpdate(User user) {
        this(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(), user.getEnabled());
    }
    
}
