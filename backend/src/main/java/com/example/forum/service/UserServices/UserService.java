package com.example.forum.service.UserServices;

import com.example.forum.entity.AppUser;

import java.util.List;

public interface UserService {
    AppUser createUser(AppUser user);
    AppUser getUserById(int id);
    List<AppUser> getUsers();
}
