package com.hub.foro.api.modules.response.dto;

import java.time.LocalDateTime;

import com.hub.foro.api.modules.response.model.Response;

public record DtoResponseRegistration(
    Long id,
    String message,
    LocalDateTime dateCreated,
    LocalDateTime dateUpdated,
    Long topicId,
    Long userId,
    Boolean enabled
) {

    public DtoResponseRegistration(Response responseSaved) {
        this(responseSaved.getId(), responseSaved.getMessage(), responseSaved.getDateCreated(), 
        responseSaved.getDateUpdated(), responseSaved.getTopicId().getId(), responseSaved.getUserId().getId(), responseSaved.getEnabled());
    }

}
