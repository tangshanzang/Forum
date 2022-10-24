package com.example.forum.service.UserServices;

import com.example.forum.dto.AppUserDTO;
import com.example.forum.entity.AppUser;
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
    private final PasswordEncoder passwordEncoder;
    @Override
    public String createUser(AppUser user) {
        String msg = "";
        if(userRepo.findByUsername(user.getUsername()) != null){
            msg = "False";
            return msg;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedTime(LocalDateTime.now());
        userRepo.save(user);
        return "True";
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
    public String updateUser(String username, String name, String password) {
        AppUser user = userRepo.findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        userRepo.save(user);
        return "true";
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
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
