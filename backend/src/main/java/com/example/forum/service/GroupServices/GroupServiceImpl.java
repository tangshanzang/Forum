package com.example.forum.service.GroupServices;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Group;
import com.example.forum.repository.GroupRepository;
import com.example.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
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
    PolicyFactory santitizer = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    private final GroupRepository groupRepo;
    private final UserRepository userRepo;
    @Override
    public String createGroup(Group group, String username) {
        AppUser user = userRepo.findByUsername(username);

        if (Objects.equals(user.getStatus(), "blocked")) {
            return "User Is Blocked";
        } else if (groupRepo.findByName(group.getName()) != null) {
            return "Group Name Is Taken";
        } else {
            Group newGroup = new Group();
            newGroup.setName(santitizer.sanitize(group.getName()));
            newGroup.setDescription(santitizer.sanitize(group.getDescription()));
            newGroup.setStatus("active");
            newGroup.setCreatedTime(LocalDateTime.now());
            newGroup.setOwner(user);
            groupRepo.save(newGroup);

            return "Group Has Been Created";
        }
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
        }else if (!Objects.equals(group.getOwner().getId(), user.getId())) {
            return "You Can't Update Other's Groups";
        }else{
            group.setDescription(santitizer.sanitize(description));
            return "Group Has Been Updated";
        }
    }

    @Override
    public String deleteGroup(String name, String username) {
        AppUser user = userRepo.findByUsername(username);
        Group group = groupRepo.findByName(name);

        if(Objects.equals(user.getStatus(), "blocked")) {
            return "User Is Blocked";
        }else if (Objects.equals(group.getStatus(), "blocked")) {
            return "Group Is Blocked";
        }else if (!Objects.equals(group.getOwner().getId(), user.getId())) {
            return "You Can't Delete Other's Groups";
        }else{
            group.setName("[Deleted Group]");
            group.setStatus("deleted");
            return "Group Has Been Deleted";
        }
    }
}
