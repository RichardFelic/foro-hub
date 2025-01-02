package com.hub.foro.api.modules.topic.PublicTopic.service;


import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hub.foro.api.modules.shared.validation.ValidationException;
import com.hub.foro.api.modules.topic.PublicTopic.dto.DtoPublicTopicList;
import com.hub.foro.api.modules.topic.dto.DtoTopicRegistration;
import com.hub.foro.api.modules.topic.dto.DtoTopicUpdate;
import com.hub.foro.api.modules.topic.model.Topic;
import com.hub.foro.api.modules.topic.repository.TopicRepository;
import com.hub.foro.api.modules.user.model.User;
import com.hub.foro.api.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicTopicService {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    // Get topic public
    public Page<DtoPublicTopicList> getTopicPublicEnabled(Pageable pageable) {
        return topicRepository.findPublicTopicsWithEnabledUsers(pageable)
                .map(topic -> new DtoPublicTopicList(topic)); 
            
            }
        // return topicRepository.findPublicTopicsWithEnabledUsers(pageable)
        //         .map(topic -> {
                    
        //             List<Response> responses = topic.getResponses() != null ? topic.getResponses().stream()
        //                     .filter(response -> response.getUser().getEnabled())
        //                     .collect(Collectors.toList()) : Collections.emptyList();
        //             return new DtoPublicTopicList(topic, responses);
        //         });
                            
        //     }
    // Get topic public by id
    public DtoPublicTopicList getTopicPublicById(Long id) {
        // Buscar el Topic y lanzar excepción si no se encuentra
        Topic topic = topicRepository.findPublicTopicByIdAndEnabled(id)
                .orElseThrow(() -> new ValidationException("Topic no disponible o usuario no habilitado"));
        
        // Convertir el Topic a DTO
        return new DtoPublicTopicList(topic);
    }
    
    // Create topic public
    public DtoPublicTopicList createTopicPublic(DtoTopicRegistration dtoTopicRegistration) {
        // Obtener el nombre de usuario autenticado
        String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        
        // Buscar el usuario autenticado
        User authenticatedUser = userRepository.findByUsername(authenticatedUsername);
                
        // Crear el Topic con los datos proporcionados y el usuario autenticado
        Topic topic = new Topic();
        topic.setCourse(dtoTopicRegistration.course());
        topic.setTitle(dtoTopicRegistration.title());
        topic.setMessage(dtoTopicRegistration.message());
        topic.setDateCreated(LocalDateTime.now());
        topic.setUser(authenticatedUser);
        topic.setEnabled(true);

        

        // Guardar el Topic
        Topic savedTopic = topicRepository.save(topic);

        // Convertir el Topic a DTO y regresar el resultado
        return new DtoPublicTopicList(savedTopic);
    }
        

    // Update topic public
    public DtoTopicUpdate updateTopicPublic(Long id, DtoTopicUpdate dtoTopicUpdate) {
        // Buscar el Topic y lanzar excepción si no se encuentra
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Topic not found with id: " + id));

        String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        if (!topic.getUser().getUsername().equals(authenticatedUsername)) {
            throw new ValidationException("You are not authorized to update this topic");
        }

        topic.updateData(dtoTopicUpdate);
        topic.setDateUpdated(LocalDateTime.now());
        
        Topic updatedTopic = topicRepository.save(topic);

        return new DtoTopicUpdate(updatedTopic);
    }

    // Delete topic public
    public void deleteTopicPublic(Long id) {
        // Buscar el Topic y lanzar excepción si no se encuentra
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Topic not found with id: " + id));

        String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User authenticatedUser = topic.getUser();

        if (!authenticatedUser.getUsername().equals(authenticatedUsername)) {
            throw new ValidationException("You are not authorized to delete this topic");
        }

        topic.setEnabled(false);
        topicRepository.save(topic);
    }

}
