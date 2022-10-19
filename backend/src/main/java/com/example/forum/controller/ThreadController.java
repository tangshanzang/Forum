package com.example.forum.controller;

import com.example.forum.entity.Thread;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/thread")
@CrossOrigin(origins = "http://localhost:8080")
public class ThreadController {

    public ResponseEntity<Thread> createThread;
    public ResponseEntity<?> deleteThread;
    public ResponseEntity<?> updateThread;
    public ResponseEntity<?> getThreadWithGroupId;
    public ResponseEntity<?> getThreadWithThreadId;
    public ResponseEntity<?> getThreadWithUserId;
    public ResponseEntity<?> getAllThreads;
}
