package com.hub.foro.api.modules.response.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hub.foro.api.modules.response.dto.DtoUpdateResponse;
import com.hub.foro.api.modules.topic.model.Topic;
import com.hub.foro.api.modules.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Response")
@Table(name = "responses")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @Column(name = "creation_date")
    private LocalDateTime dateCreated;
    @Column(name = "update_date")
    private LocalDateTime dateUpdated;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    @JsonBackReference
    private Topic topicId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    private Boolean enabled;

    public void updateData(DtoUpdateResponse dtoUpdateResponse) {
        if (dtoUpdateResponse.message() != null)
            this.message = dtoUpdateResponse.message();
        if (dtoUpdateResponse.enabled() != null)
            this.enabled = dtoUpdateResponse.enabled();
    }

}
