package com.example.forum.controller;

import com.example.forum.entity.AppUser;
import com.example.forum.service.UserServices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    private final UserService userService;

    public ResponseEntity<AppUser> createUser;
    public ResponseEntity<?> logout;
    public ResponseEntity<?> updateUser;
    public ResponseEntity<?> deleteUser;
    public ResponseEntity<?> getUserWithUserName;
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    };
}
