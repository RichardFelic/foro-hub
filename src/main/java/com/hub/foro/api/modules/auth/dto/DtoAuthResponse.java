package com.hub.foro.api.modules.auth.dto;

public record DtoAuthResponse(
    String accessToken,
    String refreshToken
) {

}
