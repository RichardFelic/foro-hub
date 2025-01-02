package com.hub.foro.api.modules.response.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hub.foro.api.modules.response.dto.DtoResponseList;
import com.hub.foro.api.modules.response.dto.DtoResponseRegistration;
import com.hub.foro.api.modules.response.dto.DtoUpdateResponse;
import com.hub.foro.api.modules.response.model.Response;
import com.hub.foro.api.modules.response.repository.ResponseRepository;
import com.hub.foro.api.modules.topic.model.Topic;
import com.hub.foro.api.modules.topic.repository.TopicRepository;
import com.hub.foro.api.modules.user.model.User;
import com.hub.foro.api.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor()
public class ResponseService {
    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    // get response
    public Page<DtoResponseList> getResponse(Pageable pageable) {
        Page<Response> responses = responseRepository.findAll(pageable);
        return responses.map(DtoResponseList::new);
    }

    // get response by id
    public DtoResponseList getResponseById(Long id) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Response not found"));
        return new DtoResponseList(response);
    }

    // Create response
    public DtoResponseRegistration createResponse(DtoResponseRegistration dtoResponseRegistration) {
        User user = userRepository.findById(dtoResponseRegistration.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Topic topic = topicRepository.findById(dtoResponseRegistration.topicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        Response response = new Response(null, dtoResponseRegistration.message(), LocalDateTime.now(), null, topic,
                user, true);
        var responseSaved = responseRepository.save(response);
        return new DtoResponseRegistration(responseSaved);
    }

    // Update response
    public DtoUpdateResponse updateResponse(Long id, DtoUpdateResponse dtoUpdateResponse) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Response not found"));

        response.updateData(dtoUpdateResponse);
        response.setDateUpdated(LocalDateTime.now());
        responseRepository.save(response);
        return new DtoUpdateResponse(response);

    }

    // Delete response
    public void deleteResponse(Long id) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Response not found"));
        response.setEnabled(false);
        responseRepository.save(response);
    }

    // Active response
    public void activeResponse(Long id) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Response not found"));
        response.setEnabled(true);
        responseRepository.save(response);
    }

}
