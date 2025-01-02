package com.hub.foro.api.modules.topic.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hub.foro.api.modules.response.model.Response;
import com.hub.foro.api.modules.topic.dto.DtoTopicUpdate;
import com.hub.foro.api.modules.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Topic")
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String course;
    private String title;
    private String message;
    @Column(name = "creation_date")
    private LocalDateTime dateCreated;
    @Column(name = "update_date")
    private LocalDateTime dateUpdated;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


    private Boolean enabled;

    @JsonManagedReference
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Response> responses = new ArrayList<>();

    public void updateData(DtoTopicUpdate dtoTopicUpdate) {
        if (dtoTopicUpdate.course() != null) {
            this.course = dtoTopicUpdate.course();
        }
        if (dtoTopicUpdate.title() != null) {
            this.title = dtoTopicUpdate.title();
        }
        if (dtoTopicUpdate.message() != null) {
            this.message = dtoTopicUpdate.message();
        }
        
    }
   
}
