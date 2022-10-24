package com.example.forum.service.UserServices;

import com.example.forum.dto.AppUserDTO;
import com.example.forum.entity.AppUser;

import java.util.List;

public interface UserService {
    String createUser(AppUser user);
    AppUser getUserByUsername(String username);
    List<AppUser> getUsers();
    Boolean deleteUser(String username);

    AppUserDTO currentUserDTO(String username);

    String updateUser(String username, String name, String password);
}
