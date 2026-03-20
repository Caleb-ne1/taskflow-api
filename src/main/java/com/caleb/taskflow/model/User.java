package com.caleb.taskflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Password required")
    @Column(nullable = false)
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    @Builder
    public User(Long id, String email, String password, List<Task> tasks) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.tasks = (tasks != null) ? tasks : new ArrayList<>();
    }

}
