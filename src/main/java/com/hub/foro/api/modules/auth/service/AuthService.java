package com.hub.foro.api.modules.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hub.foro.api.modules.auth.dto.DtoAuthResponse;
import com.hub.foro.api.modules.auth.dto.DtoLoginRequest;
import com.hub.foro.api.modules.auth.dto.DtoRegisterRequest;
import com.hub.foro.api.modules.shared.validation.ValidationException;
import com.hub.foro.api.config.security.TokenService;
import com.hub.foro.api.modules.user.model.User;
import com.hub.foro.api.modules.user.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // Register user
    public void register(DtoRegisterRequest dtoRegisterRequest) {
        User user = new User();
        user.setUsername(dtoRegisterRequest.username());
        user.setEmail(dtoRegisterRequest.email());
        user.setPassword(passwordEncoder.encode(dtoRegisterRequest.password()));
        user.setRole("USER");

        userRepository.save(user);
    }

    // Login user
    public DtoAuthResponse login(DtoLoginRequest dtoLoginRequest, HttpServletResponse response) {
        User user = userRepository.findByUsername(dtoLoginRequest.username());
        if (user == null || !passwordEncoder.matches(dtoLoginRequest.password(), user.getPassword())) {
            throw new ValidationException("Invalid username or password");
        }

        DtoAuthResponse tokens = generateTokens(user);

        // Configurar cookies con los tokens
        addCookie(response, "accessToken", tokens.accessToken(), 15 * 60); // 15 minutos
        addCookie(response, "refreshToken", tokens.refreshToken(), 24 * 60 * 60); // 24 horas

        return tokens;
    }

    // Refresh token
    public DtoAuthResponse refreshAndSetCookies(String refreshToken, HttpServletResponse response) {
        // Lógica para refrescar el token
        DtoAuthResponse tokens = tokenService.refreshAccessToken(refreshToken);

        // Configurar cookies con los nuevos tokens
        addCookie(response, "accessToken", tokens.accessToken(), 15 * 60); // 15 minutos
        addCookie(response, "refreshToken", tokens.refreshToken(), 24 * 60 * 60); // 24 horas

        return tokens;
    }

    // logout user
    public void logout(HttpServletResponse response) {
        // Expire existing cookies
        addCookie(response, "accessToken", null, 0);
        addCookie(response, "refreshToken", null, 0);
    }

    // Method to add cookies to the response
    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // Generate tokens
    private DtoAuthResponse generateTokens(User user) {
        // Generar los tokens
        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        return new DtoAuthResponse(accessToken, refreshToken);
    }

}
