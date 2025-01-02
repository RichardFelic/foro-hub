package com.hub.foro.api.modules.response.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hub.foro.api.modules.response.PublicResponse.dto.DtoPublicResponseList;
import com.hub.foro.api.modules.response.model.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    @Query("""
                SELECT new com.hub.foro.api.modules.response.PublicResponse.dto.DtoPublicResponseList(r.id, r.message, r.dateCreated, r.dateUpdated, t.id, u.id)
                FROM Response r
                LEFT JOIN r.topicId t
                LEFT JOIN r.userId u
                WHERE r.enabled = true
                AND t.enabled = true
                AND u.enabled = true
            """)
    Page<DtoPublicResponseList> findAllEnabledResponsesWithEnabledTopicAndUser(Pageable pageable);

    @Query("SELECT r FROM Response r WHERE r.id = :id AND r.enabled = true")
    Optional<Response> findByPublicResponseByIdAndEnabled(Long id);

}
