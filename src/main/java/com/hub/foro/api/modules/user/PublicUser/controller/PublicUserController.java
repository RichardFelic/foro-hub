package com.hub.foro.api.modules.user.PublicUser.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hub.foro.api.modules.user.PublicUser.dto.DtoPublicUserList;
import com.hub.foro.api.modules.user.PublicUser.service.PublicUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class PublicUserController {
    private final PublicUserService publicUserService;

    // Get user for public enabled
    @GetMapping    
    public ResponseEntity<Page<DtoPublicUserList>> getUserEnabled(@PageableDefault(sort = "id" ,size = 10)  Pageable pageable) {
        Page<DtoPublicUserList> users = publicUserService.getUserEnabled(pageable);
        return ResponseEntity.ok(users);
    }

    // Get user for public by id enabled
    @GetMapping("/{id}")
    public ResponseEntity<DtoPublicUserList> getUserPublicEnabledById(@PathVariable Long id) {
        DtoPublicUserList user = publicUserService.getUserPublicEnabledById(id);
        return ResponseEntity.ok(user);
    }

}
