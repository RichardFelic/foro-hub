package com.hub.foro.api.modules.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hub.foro.api.modules.shared.validation.ValidationException;
import com.hub.foro.api.modules.user.dto.DtoUserList;
import com.hub.foro.api.modules.user.dto.DtoUserRegistration;
import com.hub.foro.api.modules.user.dto.DtoUserUpdate;
import com.hub.foro.api.modules.user.model.User;
import com.hub.foro.api.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // Get user
    public Page<DtoUserList> getUser(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return users.map(DtoUserList::new);
      
        
    }

    // Get user by id
    public DtoUserList getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ValidationException("User not found with id: " + id);
        }
        return new DtoUserList(user);
    }

    // Create user (Registration)
    public DtoUserRegistration registerUser(DtoUserRegistration dtoUserRegistration) {
        User user = new User(dtoUserRegistration);
        user.setPassword(passwordEncoder.encode(dtoUserRegistration.password()));
        User savedUser = userRepository.save(user);
        return new DtoUserRegistration(savedUser);
    }

    // Update user
    public DtoUserUpdate updateUser(Long id, DtoUserUpdate dtoUserUpdate) {
        User user = userRepository.findById(id)
                                    .orElseThrow(() -> new ValidationException("User not found with id: " + id));

        user.updateData(dtoUserUpdate);
        if (dtoUserUpdate.password() != null){
            user.setPassword(passwordEncoder.encode(dtoUserUpdate.password()));
        } 
        
        userRepository.save(user);

        return new DtoUserUpdate(user);
    }

    // Delete user
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ValidationException("User not found with id: " + id));
        user.setEnabled(false);
        userRepository.save(user);
    }

    // Activate user
    public void activateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ValidationException("User not found with id: " + id));
        user.setEnabled(true);
        userRepository.save(user);
    }

}