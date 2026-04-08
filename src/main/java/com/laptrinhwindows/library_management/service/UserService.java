package com.laptrinhwindows.library_management.service;

import com.laptrinhwindows.library_management.model.entity.Role;
import com.laptrinhwindows.library_management.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    List<User> searchUsers(String username, Integer roleId);

    User addUser(User user);

    User updateUser(User user, boolean keepExistingPassword);

    void deleteUser(Integer id);

    User findUserById(Integer id);

    List<Role> getAllRoles();
}
