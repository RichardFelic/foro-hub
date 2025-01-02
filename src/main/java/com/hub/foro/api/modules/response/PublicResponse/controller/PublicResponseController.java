package com.hub.foro.api.modules.response.PublicResponse.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.hub.foro.api.modules.response.PublicResponse.dto.DtoPublicResponseList;
import com.hub.foro.api.modules.response.PublicResponse.dto.DtoPublicResponseRegistration;
import com.hub.foro.api.modules.response.PublicResponse.service.PublicResponseService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/response")
@RequiredArgsConstructor
public class PublicResponseController {

    private final PublicResponseService publicResponseService;

    // get response
    @GetMapping
    public ResponseEntity<Page<DtoPublicResponseList>> getResponse(
            @PageableDefault(sort = "dateCreated", size = 10) Pageable pageable) {
        Page<DtoPublicResponseList> responses = publicResponseService.getResponse(pageable);
        return ResponseEntity.ok(responses);
    }

    // get response for public by id
    @GetMapping("/{id}")
    public ResponseEntity<DtoPublicResponseList> getResponseById(@PathVariable Long id) {
        DtoPublicResponseList response = publicResponseService.getResponseById(id);
        return ResponseEntity.ok(response);
    }

    // create response
    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    @Transactional
    public ResponseEntity<DtoPublicResponseList> createResponsePublic(
            @RequestBody @Valid DtoPublicResponseRegistration dtoPublicResponseRegistration,
            UriComponentsBuilder uriComponentsBuilder) {
        DtoPublicResponseList response = publicResponseService.createResponse(dtoPublicResponseRegistration);
        URI location = uriComponentsBuilder
                .path("/api/v1/public/response/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    // update response
    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DtoPublicResponseList> updateResponse(@PathVariable Long id,
            @RequestBody @Valid DtoPublicResponseRegistration dtoPublicResponseRegistration,
            UriComponentsBuilder uriComponentsBuilder) {
        DtoPublicResponseList response = publicResponseService.updateResponse(id, dtoPublicResponseRegistration);
        URI location = uriComponentsBuilder
                .path("/api/v1/public/response/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    // delete response
    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteResponse(@PathVariable Long id) {
        publicResponseService.deleteResponse(id);
        return ResponseEntity.noContent().build();
    }
}
