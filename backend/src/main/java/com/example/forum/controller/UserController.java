package com.example.forum.controller;

import com.example.forum.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    public ResponseEntity<AppUser> createUser;
    public ResponseEntity<?> logout;
    public ResponseEntity<?> updateUser;
    public ResponseEntity<?> deleteUser;
    public ResponseEntity<?> getUserWithId;
    public ResponseEntity<?> getAllUsers;
}
