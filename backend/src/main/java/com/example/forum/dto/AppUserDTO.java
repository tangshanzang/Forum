package com.example.forum.dto;

import com.example.forum.entity.*;
import com.example.forum.entity.Thread;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Data
public class AppUserDTO {

    private String name;
    private String status;
    private String createdTime;
    private String role;
    private Collection<Thread> threads = new ArrayList<>();
    private Collection<Post> posts = new ArrayList<>();
    private Collection<Group> groups = new ArrayList<>();

    public AppUserDTO(AppUser user) {
        name = user.getName();
        status = user.getStatus();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        createdTime = user.getCreatedTime().format(formatter);
        role = user.getRole();
        threads = user.getThreads();
        posts = user.getPosts();
        groups = user.getGroups();
    }
}
