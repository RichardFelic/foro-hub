package com.hub.foro.api.modules.user.PublicUser.dto;

import java.util.List;

import com.hub.foro.api.modules.user.model.User;

public record DtoPublicUserList(
    Long id,
    String username,
    List<DtoTopicBody> topics
) {
    public DtoPublicUserList(User user) {
        this(user.getId(), user.getUsername(), user.getTopics().stream().map(topic -> new DtoTopicBody(topic)).toList());
    }

}
