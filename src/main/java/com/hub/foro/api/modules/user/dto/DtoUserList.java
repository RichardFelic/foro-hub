package com.hub.foro.api.modules.user.dto;

import java.util.List;

import com.hub.foro.api.modules.topic.model.Topic;
import com.hub.foro.api.modules.user.model.User;

public record DtoUserList(
        Long id,
        String username,
        String email,
        String role,
        Boolean enabled,
        List<Topic> topics) {
    public DtoUserList(User user) {
        this(user.getId(), user.getUsername(), user.getEmail(),
                user.getRole(), user.getEnabled(), user.getTopics());
    }

}
