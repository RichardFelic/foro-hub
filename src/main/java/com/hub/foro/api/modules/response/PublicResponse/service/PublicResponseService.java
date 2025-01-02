package com.hub.foro.api.modules.response.PublicResponse.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hub.foro.api.modules.response.PublicResponse.dto.DtoPublicResponseList;
import com.hub.foro.api.modules.response.PublicResponse.dto.DtoPublicResponseRegistration;
import com.hub.foro.api.modules.response.model.Response;
import com.hub.foro.api.modules.response.repository.ResponseRepository;
import com.hub.foro.api.modules.shared.validation.ValidationException;
import com.hub.foro.api.modules.topic.model.Topic;
import com.hub.foro.api.modules.topic.repository.TopicRepository;
import com.hub.foro.api.modules.user.model.User;
import com.hub.foro.api.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicResponseService {
    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    // get response enabled
    public Page<DtoPublicResponseList> getResponse(Pageable pageable) {
        Page<DtoPublicResponseList> responses = responseRepository
                .findAllEnabledResponsesWithEnabledTopicAndUser(pageable);
        return responses;
    }

    public DtoPublicResponseList getResponseById(Long id) {
        Response response = responseRepository.findByPublicResponseByIdAndEnabled(id)
                .orElseThrow(() -> new ValidationException("Response not found"));
        return new DtoPublicResponseList(response);
    }

    // create response
    public DtoPublicResponseList createResponse(DtoPublicResponseRegistration dtoPublicResponseRegistration) {
        String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername();

        User authenticatedUser = userRepository.findByUsername(authenticatedUsername);
        if (authenticatedUser == null || authenticatedUser.getId() == null) {
            throw new ValidationException("Authenticated user not found or ID is null.");
        }

        Topic topic = topicRepository.findById(dtoPublicResponseRegistration.topicId())
                .filter(Topic::getEnabled)
                .orElseThrow(() -> new ValidationException(
                        "Topic not found with id: " + dtoPublicResponseRegistration.topicId()));

        if (!topic.getEnabled()) {
            throw new ValidationException("Topic not found with id: " + dtoPublicResponseRegistration.topicId());
        }

        Response response = new Response();
        response.setMessage(dtoPublicResponseRegistration.message());
        response.setDateCreated(LocalDateTime.now());
        response.setDateUpdated(null);
        response.setTopicId(topic);
        response.setUserId(authenticatedUser);
        response.setEnabled(true);

        Response savedResponse = responseRepository.save(response);
        return new DtoPublicResponseList(savedResponse);
    }

    // update response
    public DtoPublicResponseList updateResponse(Long id, DtoPublicResponseRegistration dtoPublicResponseRegistration) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Response not found"));

        String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername();

        if (!response.getUserId().getUsername().equals(authenticatedUsername)) {
            throw new ValidationException("You are not authorized to update this response");
        }

        response.setMessage(dtoPublicResponseRegistration.message());
        response.setDateUpdated(LocalDateTime.now());
        Response updatedResponse = responseRepository.save(response);
        return new DtoPublicResponseList(updatedResponse);
    }

    // delete response
    public void deleteResponse(Long id) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Response not found"));

        String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername();

        if (!response.getUserId().getUsername().equals(authenticatedUsername)) {
            throw new ValidationException("You are not authorized to delete this response");
        }

        response.setEnabled(false);
        responseRepository.save(response);
    }

}
