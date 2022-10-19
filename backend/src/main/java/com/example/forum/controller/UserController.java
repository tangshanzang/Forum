package com.example.forum.controller;

import com.example.forum.entity.AppUser;
import com.example.forum.service.UserServices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestParam String username, @RequestParam String password,
                                        @RequestParam String name){
        AppUser user = new AppUser(username, password, name);
        return ResponseEntity.ok().body(userService.createUser(user));
    }

    public ResponseEntity<?> logout;
    public ResponseEntity<?> updateUser;
    public ResponseEntity<?> deleteUser;
    public ResponseEntity<?> getUserWithUserName;
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    };
}
