package com.example.forum.repository;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    Group findByName(String username);
}
