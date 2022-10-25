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
    @PutMapping("/blockUser")
    public ResponseEntity<?> blockUser(@RequestParam String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.blockUser(auth.getName(), username));
    }

    @PutMapping("/unBlockUser")
    public ResponseEntity<?> unblockUser(@RequestParam String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.unBlockUser(auth.getName(), username));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> adminDeleteUser(@RequestParam String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.deleteUser(auth.getName(), username));
    }

    // group
    @PutMapping("/blockGroup")
    public ResponseEntity<?> blockGroup(@RequestParam String name){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.blockGroup(auth.getName(), name));
    }

    @PutMapping("/unBlockGroup")
    public ResponseEntity<?> unblockGroup(@RequestParam String name){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.unBlockGroup(auth.getName(), name));
    }

    @DeleteMapping("/deleteGroup")
    public ResponseEntity<?> adminDeleteGroup(@RequestParam String name){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.deleteGroup(auth.getName(), name));
    }

    // thread
    @PutMapping("/blockThread")
    public ResponseEntity<?> blockThread(@RequestParam String title){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.blockThread(auth.getName(), title));
    }

    @PutMapping("/unBlockThread")
    public ResponseEntity<?> unBlockThread(@RequestParam String title){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.unBlockThread(auth.getName(), title));
    }

    @DeleteMapping("/deleteThread")
    public ResponseEntity<?> deleteThread(@RequestParam String title){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.deleteThread(auth.getName(), title));
    }

    // post
    @PutMapping("/blockPost")
    public ResponseEntity<?> blockPost(@RequestParam int postId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.blockPost(auth.getName(), postId));
    }

    @PutMapping("/unBlockPost")
    public ResponseEntity<?> unBlockPost(@RequestParam int postId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.unBlockPost(auth.getName(), postId));
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<?> deletePost(@RequestParam int postId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(adminService.deletePost(auth.getName(), postId));
    }
}
