package com.example.forum.service.ThreadServices;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Group;
import com.example.forum.entity.Thread;
import com.example.forum.repository.GroupRepository;
import com.example.forum.repository.ThreadRepository;
import com.example.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ThreadServiceImpl implements ThreadService{

    private final GroupRepository groupRepo;
    private final ThreadRepository threadRepo;
    private final UserRepository userRepo;

    @Override
    public String createThread(int groupId, String title, String content, String username) {
        AppUser user = userRepo.findByUsername(username);
        Optional<Group> group = groupRepo.findById(groupId);
        if (Objects.equals(user.getStatus(), "blocked")) {
            return "User Is Blocked";
        } else if (Objects.equals(group.get().getStatus(), "blocked")) {
            return "Group Is Blocked";
        } else if (threadRepo.findByTitle(title) != null) {
            return "Thread Title Is Taken";
        } else {
            Thread thread = new Thread();
            thread.setTitle(title);
            thread.setContent(content);
            thread.setCreatedTime(LocalDateTime.now());
            thread.setStatus("active");
            thread.setAuthor(user);
            thread.setGroup(group.get());
            threadRepo.save(thread);
            return "Thread Has Been Created";
        }
    }

    @Override
    public List<Thread> getThreadsOfGroup(int groupId) {
        List<Thread> threads = new ArrayList<>();
        Optional<Group> group = groupRepo.findById(groupId);
        if(group.isPresent()){
            threads = threadRepo.findByGroup(group.get());
        }
        return threads;
    }

    @Override
    public String updateThread(String title, String content, String username) {
        AppUser user = userRepo.findByUsername(username);
        Thread thread = threadRepo.findByTitle(title);

        if(Objects.equals(user.getStatus(), "blocked")) {
            return "User Is Blocked";
        }else if(Objects.equals(thread.getGroup().getStatus(), "blocked")) {
            return "Group Is Blocked";
        }else if (Objects.equals(thread.getStatus(), "blocked")) {
            return "Thread Is Blocked";
        }else if (!Objects.equals(thread.getAuthor().getName(), user.getName())) {
            return "You Can't Update Other's Thread";
        }else{
            thread.setContent(content);
            threadRepo.save(thread);
            return "Thread Has Been Updated";
        }
    }

    @Override
    public String deleteThread(String title, String username) {
        AppUser user = userRepo.findByUsername(username);
        Thread thread = threadRepo.findByTitle(title);

        if(Objects.equals(user.getStatus(), "blocked")) {
            return "User Is Blocked";
        }else if (Objects.equals(thread.getGroup().getStatus(), "blocked")) {
            return "Group Is Blocked";
        }else if (Objects.equals(thread.getStatus(), "blocked")) {
            return "Thread Is Blocked";
        }else if (!Objects.equals(thread.getAuthor().getName(), user.getName())) {
            return "You Can't Delete Other's Thread";
        }else{
            thread.setTitle("[Deleted Thread]");
            thread.setStatus("deleted");
            return "Thread Has Been Deleted";
        }
    }
}
