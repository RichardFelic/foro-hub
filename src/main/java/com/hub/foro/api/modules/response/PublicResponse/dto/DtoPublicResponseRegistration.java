package com.hub.foro.api.modules.response.PublicResponse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoPublicResponseRegistration(
    @NotBlank(message = "Message cannot be blank or null")
    String message,
    @NotNull(message = "Topic ID cannot be null")
    Long topicId
) {



}
