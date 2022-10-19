package com.example.forum.service.UserServices;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Role;

import java.util.List;

public interface UserService {
    AppUser createUser(AppUser user);
    AppUser getUserById(int id);
    List<AppUser> getUsers();
    Role createRole(Role role);
    // DELETE this later!! admin only direct edit in DB!
    void addRoleToUser(String username, String roleName);
}
