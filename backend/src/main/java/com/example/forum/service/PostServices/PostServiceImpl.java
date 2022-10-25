package com.example.forum.service.PostServices;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Group;
import com.example.forum.entity.Post;
import com.example.forum.entity.Thread;
import com.example.forum.repository.GroupRepository;
import com.example.forum.repository.PostRepository;
import com.example.forum.repository.ThreadRepository;
import com.example.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
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
public class PostServiceImpl implements  PostService{
    PolicyFactory santitizer = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    private final PostRepository postRepo;
    private final ThreadRepository threadRepo;
    private final UserRepository userRepo;
    @Override
    public String createPost(int threadId, String content, String username) {
        AppUser user = userRepo.findByUsername(username);
        Optional<Thread> thread = threadRepo.findById(threadId);
        if (Objects.equals(user.getStatus(), "blocked")) {
            return "User Is Blocked";
        } else if (Objects.equals(thread.get().getStatus(), "blocked")) {
            return "Thread Is Blocked";
        }  else {
            Post post = new Post();
            post.setCreatedTime(LocalDateTime.now());
            post.setStatus("active");
            post.setContent(santitizer.sanitize(content));
            post.setThread(thread.get());
            post.setAuthor(user);
            postRepo.save(post);
            return "Post Has Been Created";
        }
    }

    @Override
    public String updatePost(int postId, String content, String username) {
        AppUser user = userRepo.findByUsername(username);
        Optional<Post> post = postRepo.findById(postId);

        if(Objects.equals(user.getStatus(), "blocked")) {
            return "User Is Blocked";
        }else if(Objects.equals(post.get().getThread().getStatus(), "blocked")) {
            return "Thread Is Blocked";
        }else if(Objects.equals(post.get().getStatus(), "blocked")) {
            return "Post Is Blocked";
        }else if (!Objects.equals(post.get().getAuthor().getId(), user.getId())) {
            return "You Can't Update Other's Post";
        }else{
            post.get().setContent(santitizer.sanitize(content));
            postRepo.save(post.get());
            return "Post Has Been Updated";
        }
    }

    @Override
    public String deletePost(int postId, String username) {
        AppUser user = userRepo.findByUsername(username);
        Optional<Post> post = postRepo.findById(postId);

        if(Objects.equals(user.getStatus(), "blocked")) {
            return "User Is Blocked";
        }else if(Objects.equals(post.get().getThread().getStatus(), "blocked")) {
            return "Thread Is Blocked";
        }else if(Objects.equals(post.get().getStatus(), "blocked")) {
            return "Post Is Blocked";
        }else if (!Objects.equals(post.get().getAuthor().getId(), user.getId())) {
            return "You Can't Delete Other's Post";
        }else{
            post.get().setContent("[Deleted Post]");
            post.get().setStatus("deleted");
            postRepo.save(post.get());
            return "Post Has Been Deleted";
        }
    }

    @Override
    public List<Post> getPostsOfThread(int threadId) {
        List<Post> posts = new ArrayList<>();
        Optional<Thread> thread = threadRepo.findById(threadId);
        if(thread.isPresent()){
            posts = postRepo.findByThread(thread.get());
        }
        return posts;
    }
}
