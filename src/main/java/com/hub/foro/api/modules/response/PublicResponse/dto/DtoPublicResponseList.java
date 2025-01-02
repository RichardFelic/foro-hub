package com.hub.foro.api.modules.response.PublicResponse.dto;

import java.time.LocalDateTime;

import com.hub.foro.api.modules.response.model.Response;

public record DtoPublicResponseList(
    Long id,
    String message,
    LocalDateTime dateCreated,
    LocalDateTime dateUpdated,
    Long topicId,
    Long userId
) {

    public DtoPublicResponseList(DtoPublicResponseList response) {
        this(response.id, response.message, response.dateCreated, response.dateUpdated, response.topicId, response.userId);
    }

    public DtoPublicResponseList(Response response) {
        this(response.getId(), response.getMessage(), response.getDateCreated(), response.getDateUpdated(), 
        response.getTopicId().getId(), response.getUserId().getId());
    }

}
