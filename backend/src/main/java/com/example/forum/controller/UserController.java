package com.example.forum.controller;

import com.example.forum.entity.AppUser;
import com.example.forum.service.UserServices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody AppUser user){
//        AppUser user = new AppUser(username, password, name);
        return ResponseEntity.ok().body(userService.createUser(user));
    }

    public ResponseEntity<?> logout;
    public ResponseEntity<?> updateUser;
    @DeleteMapping("/delete")
    public @ResponseBody ResponseEntity<?> deleteUser(Principal principal){
        System.out.println(principal);
        return ResponseEntity.ok().body(userService.deleteUser(principal.getName()));
    }
    @GetMapping("/find")
    public ResponseEntity<?> getUserWithUserName(@RequestParam String username){
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @GetMapping("/users")
    public ResponseEntity<Resource> getAllUsers(){
        return new ResponseEntity(userService.getUsers(), HttpStatus.OK);
    }
}
