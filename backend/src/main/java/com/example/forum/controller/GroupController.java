package com.example.forum.controller;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Group;
import com.example.forum.service.GroupServices.GroupService;
import com.example.forum.service.UserServices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
@CrossOrigin
public class GroupController {

    private final GroupService groupService;
    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody Group group){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(groupService.createGroup(group, auth.getName()));
    }


    public ResponseEntity<?> deleteGroup;
    public ResponseEntity<?> updateGroup;
    public ResponseEntity<?> getGroupWithGroupName;
    public ResponseEntity<?> getGroupWithUserName;

    @GetMapping("/groups")
    public ResponseEntity<?> getAllGroups(){
        System.out.println("GETTING GROUPS");
        return ResponseEntity.ok().body(groupService.getGroups());
    }
}
