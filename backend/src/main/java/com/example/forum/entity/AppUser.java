package com.example.forum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = AUTO)
    private int id;

    private String username;

    private String password;

    private String name;

    @Column(name = "created_time")
    private LocalDateTime createdTime;


    private String role = "ROLE_USER";

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Collection<Thread> threads = new ArrayList<>();

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Collection<Post> posts = new ArrayList<>();

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Collection<Group> groups = new ArrayList<>();

    private String status = "active";

    public AppUser(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        createdTime = LocalDateTime.now();
    }
}
