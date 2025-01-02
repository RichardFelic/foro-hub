package com.hub.foro.api.modules.user.PublicUser.dto;

import java.time.LocalDateTime;

import com.hub.foro.api.modules.topic.model.Topic;

public record DtoTopicBody(
    String course,
    String title,
    String message,
    LocalDateTime dateCreated,
    LocalDateTime dateUpdated
) {

    public DtoTopicBody(Topic topic) {
        this(topic.getCourse(), topic.getTitle(), topic.getMessage(), topic.getDateCreated(), topic.getDateUpdated());
    }

}
