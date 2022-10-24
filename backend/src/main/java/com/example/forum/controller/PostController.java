package com.example.forum.controller;

import com.example.forum.entity.Post;
import com.example.forum.entity.Thread;
import com.example.forum.service.PostServices.PostService;
import com.example.forum.service.ThreadServices.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@CrossOrigin
public class PostController {

    private final PostService postService;
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestParam int threadId, @RequestParam String content){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String msg = postService.createPost(threadId, content, auth.getName());
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePost(@RequestBody Post post){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String msg = postService.updatePost(post.getId(), post.getContent(), auth.getName());
        return ResponseEntity.ok().body(msg);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePost(@RequestBody Post post){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String msg = postService.deletePost(post.getId(), auth.getName());
        return ResponseEntity.ok().body(msg);
    }

    @GetMapping("/postsOfThread")
    public ResponseEntity<?> getAllThreads(@RequestParam int threadId){
        return ResponseEntity.ok().body(postService.getPostsOfThread(threadId));
    }
}
