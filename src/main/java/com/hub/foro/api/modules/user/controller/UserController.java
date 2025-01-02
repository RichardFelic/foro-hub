package com.hub.foro.api.modules.user.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.hub.foro.api.modules.user.dto.DtoUserList;
import com.hub.foro.api.modules.user.dto.DtoUserRegistration;
import com.hub.foro.api.modules.user.dto.DtoUserUpdate;
import com.hub.foro.api.modules.user.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class UserController {
    private final UserService userService;

    // Get user
    @GetMapping
    public ResponseEntity<Page<DtoUserList>> getUser(@PageableDefault(sort = "id", size = 10) Pageable pageable) {
        Page<DtoUserList> users = userService.getUser(pageable);
        return ResponseEntity.ok(users);
    }

    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<DtoUserList> getUserById(@PathVariable Long id) {
        DtoUserList user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Create user
    @PostMapping
    @Transactional
    public ResponseEntity<DtoUserRegistration> registerUser(
            @RequestBody @Valid DtoUserRegistration dtoUserRegistration,
            UriComponentsBuilder uriComponentsBuilder) {

        DtoUserRegistration user = userService.registerUser(dtoUserRegistration);

        URI location = uriComponentsBuilder
                .path("/users/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(location).body(user);
    }

    // Update user
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DtoUserUpdate> updateUser(@PathVariable Long id,
            @RequestBody @Valid DtoUserUpdate dtoUserUpdate,
            UriComponentsBuilder uriComponentsBuilder) {
        DtoUserUpdate user = userService.updateUser(id, dtoUserUpdate);
        URI location = uriComponentsBuilder
                .path("/users/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(location).body(user);
    }

    // Delete user
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Activate user
    @PutMapping("/activate/{id}")
    @Transactional
    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
        userService.activateUser(id);
        return ResponseEntity.noContent().build();
    }
}
