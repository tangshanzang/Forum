package com.example.forum.service.AdminServices;

import com.example.forum.entity.AppUser;
import com.example.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceimpl implements AdminService{

    private final UserRepository userRepo;

    @Override
    public String blockUser(String adminUsername, String targetUsername) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To Block";
        }

        AppUser targetUser = userRepo.findByUsername(targetUsername);

        if (targetUser == null) {
            return "User Does Not Exist";
        } else if (Objects.equals(targetUser.getStatus(), "blocked")) {
            return "User Is Already Blocked";
        } else if (Objects.equals(targetUser.getStatus(), "deleted")) {
            return "User Is Deleted, Can't Block Deleted User";
        } else {
            targetUser.setStatus("blocked");
            userRepo.save(targetUser);
            return "User Has Been Blocked";
        }
    }

    @Override
    public String unBlockUser(String adminUsername, String targetUsername) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To unBlock";
        }

        AppUser targetUser = userRepo.findByUsername(targetUsername);

        if (targetUser == null) {
            return "User Does Not Exist";
        } else if (Objects.equals(targetUser.getStatus(), "active")) {
            return "User Was Not Blocked";
        } else if (Objects.equals(targetUser.getStatus(), "deleted")) {
            return "User Is Deleted, Can't unBlock Deleted User";
        } else {
            targetUser.setStatus("active");
            userRepo.save(targetUser);
            return "User Has Been unBlocked";
        }
    }

    @Override
    public String deleteUser(String adminUsername, String targetUsername) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To Delete Others";
        }

        AppUser targetUser = userRepo.findByUsername(targetUsername);

        if (targetUser == null) {
            return "User Does Not Exist";
        } else if (Objects.equals(targetUser.getStatus(), "blocked")) {
            return "User Is Blocked, Can't Delete Blocked User";
        } else if (Objects.equals(targetUser.getStatus(), "deleted")) {
            return "User Was Already Deleted";
        } else {
            targetUser.setUsername("[Deleted User]");
            targetUser.setStatus("deleted");
            userRepo.save(targetUser);
            return "User Has Been Deleted";
        }
    }


}
