package com.example.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = AUTO)
    private int id;

    private String content;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    private String status = "active";

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AppUser author;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Thread thread;

    public Post(int id, String content){
        this.id = id;
        this.content = content;
    }
}
