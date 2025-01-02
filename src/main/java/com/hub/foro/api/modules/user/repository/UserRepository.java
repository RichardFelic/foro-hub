package com.hub.foro.api.modules.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hub.foro.api.modules.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by username
    User findByUsername(String username);

    // Find user enabled
    Page<User> findByEnabled(boolean b, Pageable pageable);

    // Find user enabled by id
    @Query("select u from User u where u.enabled = ?1")
    Page<User> findEnabledById(boolean b, Pageable pageable);

    // Find user enabled by id v2
    @Query("select u from User u where u.enabled = ?1 and u.id = ?2")
    Optional<User> findEnabledById(boolean b, Long id);



}
