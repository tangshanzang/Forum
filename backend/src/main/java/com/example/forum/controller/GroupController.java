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
    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody Group group){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String msg = groupService.createGroup(group, auth.getName());
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateGroup(@RequestBody Group group){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String msg = groupService.updateGroup(group.getName(), group.getDescription(), auth.getName());
        return ResponseEntity.ok().body(msg);
    }


    public ResponseEntity<?> deleteGroup;
    public ResponseEntity<?> getGroupWithGroupName;
    public ResponseEntity<?> getGroupWithUserName;

    @GetMapping("/groups")
    public ResponseEntity<?> getAllGroups(){
        System.out.println("GETTING GROUPS");
        return ResponseEntity.ok().body(groupService.getGroups());
    }
}
