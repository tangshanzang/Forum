package com.example.forum.service.GroupServices;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Group;
import com.example.forum.repository.GroupRepository;
import com.example.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService{

    private final GroupRepository groupRepo;
    private final UserRepository userRepo;
    @Override
    public String createGroup(Group group, String username) {
        String msg = "";
        if(groupRepo.findByName(group.getName()) != null){
            msg = "False";
            return msg;
        }
        AppUser owner = userRepo.findByUsername(username);

        group.setCreatedTime(LocalDateTime.now());
        group.setOwner(owner);
        groupRepo.save(group);
        msg = "true";

        return msg;
    }

    @Override
    public List<Group> getGroups() {
        return groupRepo.findAll();
    }

    @Override
    public String updateGroup(String name, String description, String username) {
        AppUser user = userRepo.findByUsername(username);
        Group group = groupRepo.findByName(name);

        if(Objects.equals(user.getStatus(), "blocked")) {
            return "User Is Blocked";
        }else if (Objects.equals(group.getStatus(), "blocked")) {
            return "Group Is Blocked";
        }else if (group == null) {
            return "Group Does Not Exist";
        }else if (groupRepo.findByName(name) != null) {
            return "Group Name Is Taken";
        }else if (!Objects.equals(group.getOwner().getId(), user.getId())) {
            return "You Can't Delete Other's Groups";
        }else{
            group.setName(name);
            group.setDescription(description);
            return "Group Has Been Updated";
        }
    }
}
