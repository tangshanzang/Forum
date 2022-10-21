package com.example.forum.service.UserServices;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Role;

import java.util.List;

public interface UserService {
    AppUser createUser(AppUser user);
    AppUser getUserByUsername(String username);
    List<AppUser> getUsers();
    Role createRole(Role role);
    // DELETE this later!! admin only direct edit in DB!
    void addRoleToUser(String username, String roleName);
    Boolean deleteUser(String username);
}
