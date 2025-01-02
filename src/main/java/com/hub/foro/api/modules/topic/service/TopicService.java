package com.hub.foro.api.modules.topic.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hub.foro.api.modules.shared.validation.ValidationException;
import com.hub.foro.api.modules.topic.dto.DtoTopicList;
import com.hub.foro.api.modules.topic.dto.DtoTopicRegistration;
import com.hub.foro.api.modules.topic.dto.DtoTopicUpdate;
import com.hub.foro.api.modules.topic.model.Topic;
import com.hub.foro.api.modules.topic.repository.TopicRepository;
import com.hub.foro.api.modules.user.model.User;
import com.hub.foro.api.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    // Get all topics
    public Page<DtoTopicList> getAllTopics(Pageable pageable) {
        Page<Topic> topics = topicRepository.findAll(pageable);
        return topics.map(DtoTopicList::new);
    }

    // Get topic by id
    public DtoTopicList getTopicById(Long id) {
        Topic topic = topicRepository.findById(id).orElse(null);
        if (topic == null) {
            throw new ValidationException("Topic not found with id: " + id);
        }
        return new DtoTopicList(topic);
    }

    // Create topic
    public DtoTopicRegistration createTopic(DtoTopicRegistration dtoTopicRegistration) {
        User user = userRepository.findById(dtoTopicRegistration.userId())
                .orElseThrow(() -> new ValidationException("User not found with id: " + dtoTopicRegistration.userId()));

        Topic topic = new Topic(null, dtoTopicRegistration.course(), dtoTopicRegistration.title(),
                dtoTopicRegistration.message(), LocalDateTime.now(), null, user, true, null);

        return new DtoTopicRegistration(topicRepository.save(topic));
    }

    // Update topic
    public DtoTopicUpdate updateTopic(Long id, DtoTopicUpdate dtoTopicUpdate) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Topic not found with id: " + id));

        topic.updateData(dtoTopicUpdate);
        topic.setDateUpdated(LocalDateTime.now());

        topicRepository.save(topic);

        return new DtoTopicUpdate(topic);
    }

    // Delete topic
    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Topic not found with id: " + id));
        topic.setEnabled(false);
        topicRepository.save(topic);
    }

    // Active topic
    public void activeTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Topic not found with id: " + id));
        topic.setEnabled(true);
        topicRepository.save(topic);
    }

}
