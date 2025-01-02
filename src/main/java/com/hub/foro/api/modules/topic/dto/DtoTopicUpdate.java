package com.hub.foro.api.modules.topic.dto;

import java.time.LocalDateTime;

import com.hub.foro.api.modules.shared.validation.Unique;
import com.hub.foro.api.modules.topic.model.Topic;

public record DtoTopicUpdate(
    Long id,
    String course,
    @Unique(entity = Topic.class, field = "title", message = "The title already exists")
    String title,
    @Unique(entity = Topic.class, field = "message", message = "The message already exists")
    String message,
    LocalDateTime dateCreated,
    LocalDateTime dateUpdated,
    Long userId,
    Boolean enabled
) {

    public DtoTopicUpdate(Topic save) {
        this(save.getId(), save.getCourse(), save.getTitle(), save.getMessage(), save.getDateCreated(), 
        save.getDateUpdated(), save.getUser().getId(), save.getEnabled());
    }

}
