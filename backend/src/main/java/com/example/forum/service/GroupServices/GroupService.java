package com.example.forum.service.GroupServices;

import com.example.forum.entity.Group;

import java.util.List;

public interface GroupService {

    String createGroup(Group group, String username);

    List<Group> getGroups();
}
