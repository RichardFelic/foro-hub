package com.hub.foro.api.modules.topic.PublicTopic.dto;

import java.time.LocalDateTime;

import com.hub.foro.api.modules.response.model.Response;

public record DtoPublicTopicBody(
    Long id,
    String message,
    LocalDateTime dateCreated,
    LocalDateTime dateUpdated,
    Long userId
) {

    public DtoPublicTopicBody(Response response) {
        this(response.getId(), response.getMessage(), response.getDateCreated(), response.getDateUpdated(), response.getUserId().getId());}

}
