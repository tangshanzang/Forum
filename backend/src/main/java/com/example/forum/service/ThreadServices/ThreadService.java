package com.example.forum.service.ThreadServices;

import com.example.forum.entity.Group;
import com.example.forum.entity.Thread;

import java.util.List;

public interface ThreadService {
    String createThread(int groupId, String title, String content, String username);

    List<Thread> getThreadsOfGroup(int groupId);

    String updateThread(String title, String content, String username);

    String deleteThread(String title, String username);
}
