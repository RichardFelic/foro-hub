package com.hub.foro.api.modules.topic.PublicTopic.dto;

import java.time.LocalDateTime;

public record DtoPublicTopicRegistration(
    Long id,
    String course,
    String title,
    String message,
    LocalDateTime dateCreated,
    Long userId
) {

}
