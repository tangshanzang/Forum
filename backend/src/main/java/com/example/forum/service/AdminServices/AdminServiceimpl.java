package com.example.forum.service.AdminServices;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Group;
import com.example.forum.entity.Post;
import com.example.forum.entity.Thread;
import com.example.forum.repository.GroupRepository;
import com.example.forum.repository.PostRepository;
import com.example.forum.repository.ThreadRepository;
import com.example.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceimpl implements AdminService{

    private final UserRepository userRepo;
    private final GroupRepository groupRepo;
    private final ThreadRepository threadRepo;
    private final PostRepository postRepo;

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
        } else if (!Objects.equals(targetUser.getStatus(), "blocked")) {
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

    @Override
    public String blockGroup(String adminUsername, String groupName) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To Block";
        }

        Group group = groupRepo.findByName(groupName);

        if (group == null) {
            return "Group Does Not Exist";
        } else if (Objects.equals(group.getStatus(), "blocked")) {
            return "Group Is Already Blocked";
        } else if (Objects.equals(group.getStatus(), "deleted")) {
            return "Group Is Deleted, Can't Block Deleted Group";
        } else {
            group.setStatus("blocked");
            groupRepo.save(group);
            return "Group Has Been Blocked";
        }
    }

    @Override
    public String unBlockGroup(String adminUsername, String groupName) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To unBlock";
        }

        Group group = groupRepo.findByName(groupName);

        if (group == null) {
            return "Group Does Not Exist";
        } else if (!Objects.equals(group.getStatus(), "blocked")) {
            return "Group Was Not Blocked";
        } else if (Objects.equals(group.getStatus(), "deleted")) {
            return "Group Is Deleted, Can't unBlock Deleted Group";
        } else {
            group.setStatus("blocked");
            groupRepo.save(group);
            return "Group Has Been unBlocked";
        }
    }

    @Override
    public String deleteGroup(String adminUsername, String groupName) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To Delete Others";
        }

        Group group = groupRepo.findByName(groupName);

        if (group == null) {
            return "Group Does Not Exist";
        } else if (Objects.equals(group.getStatus(), "blocked")) {
            return "Group Is Blocked, Can't Delete Blocked Group";
        } else if (Objects.equals(group.getStatus(), "deleted")) {
            return "Group Is Already Deleted";
        } else {
            group.setStatus("deleted");
            group.setName("[Deleted Group]");
            groupRepo.save(group);
            return "Group Has Been Deleted";
        }
    }

    @Override
    public String blockThread(String adminUsername, String threadTitle) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To Block";
        }

        Thread thread = threadRepo.findByTitle(threadTitle);

        if (thread == null) {
            return "Thread Does Not Exist";
        } else if (Objects.equals(thread.getStatus(), "blocked")) {
            return "Thread Is Already Blocked";
        } else if (Objects.equals(thread.getStatus(), "deleted")) {
            return "Thread Is Deleted, Can't Block Deleted Thread";
        } else {
            thread.setStatus("blocked");
            threadRepo.save(thread);
            return "Thread Has Been Blocked";
        }
    }

    @Override
    public String unBlockThread(String adminUsername, String threadTitle) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To unBlock";
        }

        Thread thread = threadRepo.findByTitle(threadTitle);

        if (thread == null) {
            return "Thread Does Not Exist";
        } else if (!Objects.equals(thread.getStatus(), "blocked")) {
            return "Thread Was Not Blocked";
        } else if (Objects.equals(thread.getStatus(), "deleted")) {
            return "Thread Is Deleted, Can't unBlock Deleted Thread";
        } else {
            thread.setStatus("blocked");
            threadRepo.save(thread);
            return "Thread Has Been unBlocked";
        }
    }

    @Override
    public String deleteThread(String adminUsername, String threadTitle) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To Delete Others";
        }

        Thread thread = threadRepo.findByTitle(threadTitle);

        if (thread == null) {
            return "Thread Does Not Exist";
        } else if (Objects.equals(thread.getStatus(), "blocked")) {
            return "Thread Is Blocked, Can't Delete Blocked Thread";
        } else if (Objects.equals(thread.getStatus(), "deleted")) {
            return "Thread Is Already Deleted";
        } else {
            thread.setStatus("deleted");
            thread.setTitle("[Deleted Thread]");
            threadRepo.save(thread);
            return "Thread Has Been Deleted";
        }
    }

    @Override
    public String blockPost(String adminUsername, int postId) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To Block";
        }

        Optional<Post> post = postRepo.findById(postId);

        if (post.isEmpty()) {
            return "Post Does Not Exist";
        } else if (Objects.equals(post.get().getStatus(), "blocked")) {
            return "Post Is Already Blocked";
        } else if (Objects.equals(post.get().getStatus(), "deleted")) {
            return "Post Is Deleted, Can't Block Deleted Post";
        } else {
            post.get().setStatus("blocked");
            postRepo.save(post.get());
            return "Post Has Been Blocked";
        }
    }

    @Override
    public String unBlockPost(String adminUsername, int postId) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To unBlock";
        }

        Optional<Post> post = postRepo.findById(postId);

        if (post.isEmpty()) {
            return "Post Does Not Exist";
        } else if (!Objects.equals(post.get().getStatus(), "blocked")) {
            return "Post Was Not Blocked";
        } else if (Objects.equals(post.get().getStatus(), "deleted")) {
            return "Post Is Deleted, Can't unBlock Deleted Post";
        } else {
            post.get().setStatus("blocked");
            postRepo.save(post.get());
            return "Post Has Been unBlocked";
        }
    }

    @Override
    public String deletePost(String adminUsername, int postId) {
        AppUser admin = userRepo.findByUsername(adminUsername);

        if (!Objects.equals(admin.getRole(), "ROLE_ADMIN")) {
            return "Only Admin Is Allowed To Delete Others";
        }

        Optional<Post> post = postRepo.findById(postId);

        if (post.isEmpty()) {
            return "Post Does Not Exist";
        } else if (Objects.equals(post.get().getStatus(), "blocked")) {
            return "Post Is Blocked, Can't Delete Blocked Post";
        } else if (Objects.equals(post.get().getStatus(), "deleted")) {
            return "Post Is Already Deleted";
        } else {
            post.get().setStatus("deleted");
            post.get().setContent("[Deleted Post]");
            postRepo.save(post.get());
            return "Post Has Been Deleted";
        }
    }


}
