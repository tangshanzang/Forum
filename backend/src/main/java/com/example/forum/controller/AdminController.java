package com.example.forum.controller;

import com.example.forum.service.AdminServices.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    // users
    @PutMapping("/block")
    public ResponseEntity<?> blockUser(@RequestParam String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.blockUser(auth.getName(), username));
    }

    @PutMapping("/unBlock")
    public ResponseEntity<?> unblockUser(@RequestParam String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.unBlockUser(auth.getName(), username));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> adminDeleteUser(@RequestParam String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.deleteUser(auth.getName(), username));
    }

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
