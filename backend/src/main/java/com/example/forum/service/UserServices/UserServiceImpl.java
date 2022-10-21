package com.example.forum.service.UserServices;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Role;
import com.example.forum.repository.RoleRepository;
import com.example.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AppUser createUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findByName("ROLE_USER");
        user.getRoles().add(role);
        user.setCreatedTime(LocalDateTime.now());
        return userRepo.save(user);
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public Role createRole(Role role) {
        return roleRepo.save(role);
    }

    // DELETE LATER!!
    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Boolean deleteUser(String username) {
        Boolean isDeleted = false;
        AppUser user = userRepo.findByUsername(username);
        userRepo.delete(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user in db, create authority for every role. userDetail in return#
//            public User(String username, String password, Collection<? extends GrantedAuthority > authorities) {
        return null;
    }
}
