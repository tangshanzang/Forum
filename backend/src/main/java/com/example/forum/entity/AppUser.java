package com.example.forum.entity;

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

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL
    )
    private Collection<Thread> threads = new ArrayList<>();

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL
    )
    private Collection<Post> posts = new ArrayList<>();

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL
    )
    private Collection<Group> groups = new ArrayList<>();

    private String status = "active";
}
