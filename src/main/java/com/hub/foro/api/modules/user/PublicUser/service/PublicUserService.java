package com.hub.foro.api.modules.user.PublicUser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hub.foro.api.modules.shared.validation.ValidationException;
import com.hub.foro.api.modules.user.PublicUser.dto.DtoPublicUserList;
import com.hub.foro.api.modules.user.model.User;
import com.hub.foro.api.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicUserService {
    private final UserRepository userRepository;

    public Page<DtoPublicUserList> getUserEnabled(Pageable pageable) {
        Page<User> users = userRepository.findEnabledById(true, pageable);
        return users.map(DtoPublicUserList::new);
    }

    public DtoPublicUserList getUserPublicEnabledById(Long id) {
        User user = userRepository.findEnabledById(true, id).orElse(null);
        if (user == null) {
            throw new ValidationException("User not found with id: " + id);
        }
        return new DtoPublicUserList(user);
    }
}
