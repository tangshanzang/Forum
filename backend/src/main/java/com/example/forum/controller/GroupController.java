package com.example.forum.controller;

import com.example.forum.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
@CrossOrigin
public class GroupController {

    public ResponseEntity<Group> createGroup;
    public ResponseEntity<?> deleteGroup;
    public ResponseEntity<?> updateGroup;
    public ResponseEntity<?> getGroupWithGroupName;
    public ResponseEntity<?> getGroupWithUserName;
    public ResponseEntity<?> getAllGroups;
}
