package com.hub.foro.api.modules.topic.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.hub.foro.api.modules.response.model.Response;
import com.hub.foro.api.modules.topic.model.Topic;

public record DtoTopicList(
    Long id,
    String course,
    String title,
    String message,
    LocalDateTime dateCreated,
    LocalDateTime dateUpdated,
    Long userId,
    Boolean enabled,
    List<Response> responses
) {
    public DtoTopicList(Topic topic) {
        this(topic.getId(), topic.getCourse(), topic.getTitle(), topic.getMessage(), topic.getDateCreated(), 
        topic.getDateUpdated(), topic.getUser().getId(), topic.getEnabled(), topic.getResponses().stream().limit(1).toList());
    }

}
