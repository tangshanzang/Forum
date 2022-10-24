package com.example.forum.repository;

import com.example.forum.entity.Post;
import com.example.forum.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByThread(Thread thread);
}
