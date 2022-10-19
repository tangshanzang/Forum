package com.example.forum.service.UserServices;

import com.example.forum.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    @Override
    public AppUser createUser(AppUser user) {
        return null;
    }

    @Override
    public AppUser getUserById(int id) {
        return null;
    }

    @Override
    public List<AppUser> getUsers() {
        return null;
    }
}
