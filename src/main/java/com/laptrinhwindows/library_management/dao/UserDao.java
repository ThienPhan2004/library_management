package com.laptrinhwindows.library_management.dao;

import com.laptrinhwindows.library_management.model.entity.User;

import java.util.List;

public interface UserDao {
    User findByUsernameAndPassword(String username, String password);

    List<User> findAll();

    List<User> search(String username, Integer roleId);

    User save(User user);

    User update(User user);

    void deleteById(Integer id);

    User findById(Integer id);

    boolean existsByUsername(String username);

    boolean existsByUsernameExceptId(String username, Integer id);
}
