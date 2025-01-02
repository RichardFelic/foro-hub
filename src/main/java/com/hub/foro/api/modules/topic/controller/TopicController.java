package com.hub.foro.api.modules.topic.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.hub.foro.api.modules.topic.dto.DtoTopicList;
import com.hub.foro.api.modules.topic.dto.DtoTopicRegistration;
import com.hub.foro.api.modules.topic.dto.DtoTopicUpdate;
import com.hub.foro.api.modules.topic.service.TopicService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/api/v1/topic")
public class TopicController {

    private final TopicService topicService;

    // Get all topics
    @GetMapping
    public ResponseEntity<Page<DtoTopicList>> getAllTopics(@PageableDefault( sort = "dateCreated" ,size = 10) Pageable pageable) {
        Page <DtoTopicList> topics = topicService.getAllTopics(pageable);
        return ResponseEntity.ok(topics);
    }

    // Get topic by id
    @GetMapping("/{id}")
    public ResponseEntity<DtoTopicList> getTopicById(@PathVariable Long id) {
        DtoTopicList topic = topicService.getTopicById(id);
        return ResponseEntity.ok(topic);
    }

    // Create topic
    @PostMapping
    @Transactional
    public ResponseEntity<DtoTopicRegistration> createTopic(@RequestBody @Valid DtoTopicRegistration dtoTopicRegistration, UriComponentsBuilder uriComponentsBuilder) {
        DtoTopicRegistration topic = topicService.createTopic(dtoTopicRegistration);
        URI location = uriComponentsBuilder
                .path("/api/v1/topic/{id}")
                .buildAndExpand(topic.id())
                .toUri();
        return ResponseEntity.created(location).body(topic);
    }

    // Update topic
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DtoTopicUpdate> updateTopic(@PathVariable Long id, @RequestBody @Valid DtoTopicUpdate dtoTopicUpdate, UriComponentsBuilder uriComponentsBuilder) {
        DtoTopicUpdate topic = topicService.updateTopic(id, dtoTopicUpdate);
        URI location = uriComponentsBuilder
                .path("/api/v1/topic/{id}")
                .buildAndExpand(topic.id())
                .toUri();
        return ResponseEntity.created(location).body(topic);
    }

    // Delete topic
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }

    // Active topic
    @PutMapping("/activate/{id}")
    @Transactional
    public ResponseEntity<Void> activeTopic(@PathVariable Long id) {
        topicService.activeTopic(id);
        return ResponseEntity.noContent().build();
    }
}
