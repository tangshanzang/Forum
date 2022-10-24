package com.example.forum.service.GroupServices;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Group;
import com.example.forum.repository.GroupRepository;
import com.example.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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
}
