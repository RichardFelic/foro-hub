package com.hub.foro.api.modules.topic.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.hub.foro.api.modules.shared.validation.Unique;
import com.hub.foro.api.modules.topic.model.Topic;

import jakarta.validation.constraints.NotBlank;

public record DtoTopicRegistration(
    Long id,
    @NotBlank(message = "Course cannot be blank or null")
    String course,
    @NotBlank(message = "Title cannot be blank or null")
    @Unique(entity = Topic.class, field = "title", message = "The title already exists")
    String title,
    @NotBlank(message = "Message cannot be blank or null")
    @Unique(entity = Topic.class, field = "message", message = "The message already exists")
    String message,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime dateCreated,
    Long userId,
    Boolean enabled
) {

    public DtoTopicRegistration(Topic savedTopic) {
        this(savedTopic.getId(), savedTopic.getCourse(), savedTopic.getTitle(), savedTopic.getMessage(), savedTopic.getDateCreated(), 
        savedTopic.getUser().getId(), savedTopic.getEnabled());
    }
  
}
