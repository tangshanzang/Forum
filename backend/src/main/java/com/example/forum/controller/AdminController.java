package com.example.forum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:8080")
public class AdminController {

    // users
    public ResponseEntity<?> blockUser;
    public ResponseEntity<?> unblockUser;
    public ResponseEntity<?> restoreUser;
    public ResponseEntity<?> adminDeleteUser;

    // group
    public ResponseEntity<?> blockGroup;
    public ResponseEntity<?> unblockGroup;
    public ResponseEntity<?> restoreGroup;
    public ResponseEntity<?> adminDeleteGroup;

    // thread
    public ResponseEntity<?> blockThread;
    public ResponseEntity<?> unblockThread;
    public ResponseEntity<?> restoreThread;
    public ResponseEntity<?> adminDeleteThread;

    // post
    public ResponseEntity<?> blockPost;
    public ResponseEntity<?> unblockPost;
    public ResponseEntity<?> restorePost;
    public ResponseEntity<?> adminDeletePost;
}
