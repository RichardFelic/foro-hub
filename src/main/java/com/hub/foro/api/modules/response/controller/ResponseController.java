package com.hub.foro.api.modules.response.controller;

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

import com.hub.foro.api.modules.response.dto.DtoResponseList;
import com.hub.foro.api.modules.response.dto.DtoResponseRegistration;
import com.hub.foro.api.modules.response.dto.DtoUpdateResponse;
import com.hub.foro.api.modules.response.service.ResponseService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/api/v1/response")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    // get response
    @GetMapping
    public ResponseEntity<Page<DtoResponseList>> getResponse(
            @PageableDefault(sort = "dateCreated", size = 10) Pageable pageable) {

        Page<DtoResponseList> responses = responseService.getResponse(pageable);
        return ResponseEntity.ok(responses);

    }

    // get response by id
    @GetMapping("/{id}")
    public ResponseEntity<DtoResponseList> getResponseById(@PathVariable Long id) {
        DtoResponseList response = responseService.getResponseById(id);
        return ResponseEntity.ok(response);
    }

    // Create response
    @PostMapping
    @Transactional
    public ResponseEntity<DtoResponseRegistration> createResponse(
            @RequestBody DtoResponseRegistration dtoResponseRegistration, UriComponentsBuilder uriComponentsBuilder) {

        DtoResponseRegistration response = responseService.createResponse(dtoResponseRegistration);
        URI location = uriComponentsBuilder
                .path("/api/v1/response/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    // Update response
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DtoUpdateResponse> updateResponse(@PathVariable Long id,
            @RequestBody DtoUpdateResponse dtoUpdateResponse, UriComponentsBuilder uriComponentsBuilder) {

        DtoUpdateResponse response = responseService.updateResponse(id, dtoUpdateResponse);
        URI location = uriComponentsBuilder
                .path("/api/v1/response/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    // Delete response
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteResponse(@PathVariable Long id) {
        responseService.deleteResponse(id);
        return ResponseEntity.noContent().build();
    }

    // Active response
    @PutMapping("/active/{id}")
    @Transactional
    public ResponseEntity<Void> activeResponse(@PathVariable Long id) {
        responseService.activeResponse(id);
        return ResponseEntity.noContent().build();
    }
}
