package com.hub.foro.api.modules.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hub.foro.api.modules.auth.dto.DtoAuthResponse;
import com.hub.foro.api.modules.auth.dto.DtoLoginRequest;
import com.hub.foro.api.modules.auth.dto.DtoRegisterRequest;
import com.hub.foro.api.modules.auth.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid DtoRegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<DtoAuthResponse> login(@RequestBody @Valid DtoLoginRequest request,
            HttpServletResponse response) {
        try {
            DtoAuthResponse responseLogin = authService.login(request, response);
            return ResponseEntity.ok(responseLogin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DtoAuthResponse(e.getMessage(), ""));
        }
    }

    // Refresh token
    @PostMapping("/refresh")
    @Transactional
    public ResponseEntity<DtoAuthResponse> refreshToken(
            @CookieValue(value = "refreshToken", defaultValue = "") String refreshToken,
            HttpServletResponse response) {

        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.badRequest().body(new DtoAuthResponse("Refresh token is required", ""));
        }

        try {
            // Delegate token refresh logic to the service
            DtoAuthResponse tokens = authService.refreshAndSetCookies(refreshToken, response);
            return ResponseEntity.ok(tokens);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DtoAuthResponse(e.getMessage(), ""));
        }
    }

    // Logout user
    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<String> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok("Logout successful");
    }

}
