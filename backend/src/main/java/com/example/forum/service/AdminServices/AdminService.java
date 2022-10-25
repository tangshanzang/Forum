package com.example.forum.service.AdminServices;

public interface AdminService {
    String blockUser(String adminUsername, String targetUsername);

    String unBlockUser(String adminUsername, String targetUsername);

    String deleteUser(String adminUsername, String targetUsername);

    String blockGroup(String adminUsername, String groupName);
    String unBlockGroup(String adminUsername, String groupName);
    String deleteGroup(String adminUsername, String groupName);

}
