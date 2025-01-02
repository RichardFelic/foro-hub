package com.hub.foro.api.modules.topic.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hub.foro.api.modules.topic.PublicTopic.dto.DtoPublicTopicList;
import com.hub.foro.api.modules.topic.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("SELECT t FROM Topic t WHERE t.enabled = true AND t.user.enabled = true")
    Page<DtoPublicTopicList> findPublicTopicsWithEnabledUsers(Pageable pageable);

    @Query("SELECT t FROM Topic t WHERE t.id = :id AND t.enabled = true AND t.user.enabled = true")
    Optional<Topic> findPublicTopicByIdAndEnabled(Long id);

}
