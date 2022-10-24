package com.example.forum.repository;

import com.example.forum.entity.Group;
import com.example.forum.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Integer> {
    Thread findByTitle(String title);
    List<Thread> findByGroup(Group group);
}
