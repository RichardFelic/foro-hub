package com.hub.foro.api.modules.response.dto;

import java.time.LocalDateTime;

import com.hub.foro.api.modules.response.model.Response;

public record DtoUpdateResponse(
    Long id,
    String message,
    LocalDateTime dateCreated,
    LocalDateTime dateUpdated,
    Long topicId,
    Long userId,
    Boolean enabled    
    
) {

    public DtoUpdateResponse(Response response) {
        this(response.getId(), response.getMessage(), response.getDateCreated(), response.getDateUpdated(), 
        response.getTopicId().getId(), response.getUserId().getId(), response.getEnabled());
    }

}
