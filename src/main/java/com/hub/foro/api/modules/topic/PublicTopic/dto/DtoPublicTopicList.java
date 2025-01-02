package com.hub.foro.api.modules.topic.PublicTopic.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.hub.foro.api.modules.topic.model.Topic;

public record DtoPublicTopicList(
    long id,
    String course,
    String title,
    String message,
    LocalDateTime dateCreated,
    LocalDateTime dateUpdated,
    long userId,
    List<DtoPublicTopicBody> responses
) {
    public DtoPublicTopicList(Topic topic) {
        this(
            topic.getId(),
            topic.getCourse(),
            topic.getTitle(),
            topic.getMessage(),
            topic.getDateCreated(),
            topic.getDateUpdated(),
            topic.getUser().getId(),
            topic.getResponses().stream()
                .filter(response -> response.getEnabled() && response.getUserId().getEnabled()) // Filter enabled responses and users
                .map(DtoPublicTopicBody::new) // Map to DTO
                .toList()
        );
    }

    public DtoPublicTopicList(DtoPublicTopicList topic) {
        this(topic.id, topic.course, topic.title, topic.message, topic.dateCreated, topic.dateUpdated, topic.userId, 
        topic.responses);
    }
}
