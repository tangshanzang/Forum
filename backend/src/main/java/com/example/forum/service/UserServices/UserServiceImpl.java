package com.example.forum.service.UserServices;

import com.example.forum.dto.AppUserDTO;
import com.example.forum.entity.AppUser;
import com.example.forum.entity.Role;
import com.example.forum.repository.RoleRepository;
import com.example.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
//        Role role = roleRepo.findByName("ROLE_SUPERADMIN");
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
        AppUser user = userRepo.findByUsername(username);
        user.setUsername("[Deleted]");
        return true;
    }

    @Override
    public AppUserDTO currentUserDTO(String username) {
        AppUser user = userRepo.findByUsername(username);
        AppUserDTO appUserDTO = new AppUserDTO(user);
        return appUserDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user in db, create authority for every role. userDetail in return#
//            public User(String username, String password, Collection<? extends GrantedAuthority > authorities) {
        AppUser user = userRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
