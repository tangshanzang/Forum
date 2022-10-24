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
public class Thread {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String title;

    private String content;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    private String status = "active";

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AppUser author;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(
            mappedBy = "thread",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Collection<Post> posts = new ArrayList<>();
}
