package com.hub.foro.api.config.security;

import java.util.Date;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hub.foro.api.modules.auth.dto.DtoAuthResponse;
import com.hub.foro.api.modules.shared.validation.ValidationException;
import com.hub.foro.api.modules.user.model.User;

@Service
public class TokenService {
     @Value("${api.security.jwt.secret}")
    private String SECRET_KEY;

    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 minutos
    private static final long REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000; // 24 horas

    // Generate Access Token
    public String generateAccessToken(User user) {
        return generateToken(user, ACCESS_TOKEN_EXPIRATION);
    }

    // Generate Refresh Token
    public String generateRefreshToken(User user) {
        return generateToken(user, REFRESH_TOKEN_EXPIRATION);
    }

    // Private helper to generate tokens
    private String generateToken(User user, long expirationTime) {
        try {
            var algorithm = Algorithm.HMAC512(SECRET_KEY);
            return JWT.create()
                    .withIssuer("foro-hub")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error creating JWT token", exception);
        }
    }

    // Validate Token
    public boolean validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC512(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("foro-hub").build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extract Username from Token
    public String extractUsername(String token) {
        try {
            var algorithm = Algorithm.HMAC512(SECRET_KEY);
            return JWT.require(algorithm)
                      .withIssuer("foro-hub") // Verifica que el token tenga el emisor esperado
                      .build()
                      .verify(token)
                      .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired token", e);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting username from token", e);
        }
    }

    // Refresh Access Token
    public DtoAuthResponse refreshAccessToken(String refreshToken) {
        try {
            var algorithm = Algorithm.HMAC512(SECRET_KEY);
            var verifier = JWT.require(algorithm).withIssuer("foro-hub").build();
            var decodedJWT = verifier.verify(refreshToken);

            // Validate expiration
            if (decodedJWT.getExpiresAt().before(new Date())) {
                throw new ValidationException("Refresh token has expired");
            }

            // Generate new tokens
            String username = decodedJWT.getSubject();
            Long userId = decodedJWT.getClaim("id").asLong();

            // Mock user creation, replace with actual user fetching logic
            User user = new User(userId, username, null, null, null, null, null); // Replace with user repository logic

            String newAccessToken = generateAccessToken(user);
            String newRefreshToken = generateRefreshToken(user);

            return new DtoAuthResponse(newAccessToken, newRefreshToken);

        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token", e);
        }
    }

}
