package com.example.forum.controller;

import com.example.forum.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:8080")
public class PostController {

    public ResponseEntity<Post> createPost;
    public ResponseEntity<?> deletePost;
    public ResponseEntity<?> updatePost;
    public ResponseEntity<?> getPostWithThreadName;
    public ResponseEntity<?> getPostWithPostName;
    public ResponseEntity<?> getPostWithUserName;
    public ResponseEntity<?> getAllPosts;
}
