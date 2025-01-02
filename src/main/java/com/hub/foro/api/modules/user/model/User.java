package com.hub.foro.api.modules.user.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hub.foro.api.modules.topic.model.Topic;
import com.hub.foro.api.modules.user.dto.DtoUserRegistration;
import com.hub.foro.api.modules.user.dto.DtoUserUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private String role;
    private Boolean enabled;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics;
    
    public User(DtoUserRegistration dtoUserRegistration) {
        this.username = dtoUserRegistration.username();
        this.email = dtoUserRegistration.email();
        this.password = dtoUserRegistration.password();
        this.role = dtoUserRegistration.role();
        this.enabled = true;
    }

    public void updateData(DtoUserUpdate dtoUserUpdate) {
        this.username = dtoUserUpdate.username() == null ? this.username : dtoUserUpdate.username();
        this.email = dtoUserUpdate.email() == null ? this.email : dtoUserUpdate.email();
        this.password = dtoUserUpdate.password() == null ? this.password : dtoUserUpdate.password();
        this.role = dtoUserUpdate.role() == null ? this.role : dtoUserUpdate.role();
        this.enabled = dtoUserUpdate.enabled() == null ? this.enabled : dtoUserUpdate.enabled();
    }
    
}
