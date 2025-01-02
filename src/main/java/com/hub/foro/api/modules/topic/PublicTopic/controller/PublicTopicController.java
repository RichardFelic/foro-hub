package com.hub.foro.api.modules.topic.PublicTopic.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import com.hub.foro.api.modules.shared.validation.ValidationException;
import com.hub.foro.api.modules.topic.PublicTopic.dto.DtoPublicTopicList;
import com.hub.foro.api.modules.topic.PublicTopic.service.PublicTopicService;
import com.hub.foro.api.modules.topic.dto.DtoTopicRegistration;
import com.hub.foro.api.modules.topic.dto.DtoTopicUpdate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/topic")
@RequiredArgsConstructor
public class PublicTopicController {
    private final PublicTopicService publicTopicService;

    // Get topic for public enabled
    @GetMapping
    public ResponseEntity<Page<DtoPublicTopicList>> getTopicPublicEnabled(@PageableDefault(sort = "id" ,size = 10)  Pageable pageable) {
        Page<DtoPublicTopicList> topics = publicTopicService.getTopicPublicEnabled(pageable);
        return ResponseEntity.ok(topics);
    }

    // Get topic for public by id
    @GetMapping("/{id}")
    public ResponseEntity<DtoPublicTopicList> getTopicPublicById(@PathVariable Long id) {
        DtoPublicTopicList topic = publicTopicService.getTopicPublicById(id);
        return ResponseEntity.ok(topic);
    }
 
    
    // Create topic public
    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    @Transactional
    public ResponseEntity<DtoPublicTopicList> createTopicPublic(@RequestBody @Valid DtoTopicRegistration dtoTopicRegistration, UriComponentsBuilder uriComponentsBuilder) {
        DtoPublicTopicList topic = publicTopicService.createTopicPublic(dtoTopicRegistration);
        URI location = uriComponentsBuilder
                .path("/api/v1/public/topic/{id}")
                .buildAndExpand(topic.id())
                .toUri();
        return ResponseEntity.created(location).body(topic);
    }

    // Update topic public
    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateTopicPublic(
        @PathVariable Long id, 
        @RequestBody @Valid DtoTopicUpdate dtoTopicUpdate,
        UriComponentsBuilder uriComponentsBuilder) {
        try {
            DtoTopicUpdate topic = publicTopicService.updateTopicPublic(id, dtoTopicUpdate);
            URI location = uriComponentsBuilder
                    .path("/api/v1/public/topic/{id}")
                    .buildAndExpand(topic.id())
                    .toUri();
            return ResponseEntity.created(location).body(topic);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Delete topic public
    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> deleteTopicPublic(@PathVariable Long id) {
        try {
            publicTopicService.deleteTopicPublic(id);
            return ResponseEntity.noContent().build();
        } catch (ValidationException e) {
            Map<String, String> response = Map.of("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

}
