package com.laptrinhwindows.library_management.dao;

import com.laptrinhwindows.library_management.model.entity.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();

    Role findById(Integer id);
}
