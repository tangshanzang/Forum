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
@Table(name="\"group\"")
public class Group {

    @Id
    @GeneratedValue(strategy = AUTO)
    private int id;

    private String name;

    private String status = "active";

    private String description;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL
    )
    private Collection<Thread> threads = new ArrayList<>();
}
