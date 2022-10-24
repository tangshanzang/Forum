package com.example.forum.controller;

import com.example.forum.entity.Group;
import com.example.forum.entity.Thread;
import com.example.forum.service.GroupServices.GroupService;
import com.example.forum.service.ThreadServices.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/thread")
@CrossOrigin
public class ThreadController {

    private final ThreadService threadService;
    @PostMapping("/create")
    public ResponseEntity<?> createThread(@RequestParam int groupId, @RequestParam String title, @RequestParam String content){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String msg = threadService.createThread(groupId, title, content, auth.getName());
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateThread(@RequestBody Thread thread){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String msg = threadService.updateThread(thread.getTitle(), thread.getContent(), auth.getName());
        return ResponseEntity.ok().body(msg);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteThread(@RequestParam String title){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String msg = threadService.deleteThread(title, auth.getName());
        return ResponseEntity.ok().body(msg);
    }

    @GetMapping("/threadsOfGroup")
    public ResponseEntity<?> getAllThreads(@RequestParam int groupId){
        return ResponseEntity.ok().body(threadService.getThreadsOfGroup(groupId));
    }
}
