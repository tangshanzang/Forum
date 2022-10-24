package com.example.forum.service.PostServices;

import com.example.forum.entity.Post;

import java.util.List;

public interface PostService {
    String createPost(int threadId, String content, String username);

    String updatePost(int postId, String content, String username);

    String deletePost(int postId, String username);

    List<Post> getPostsOfThread(int threadId);
}
