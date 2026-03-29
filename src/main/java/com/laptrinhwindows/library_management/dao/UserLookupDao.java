package com.laptrinhwindows.library_management.dao;

import com.laptrinhwindows.library_management.model.entity.User;

public interface UserLookupDao {
    User findById(Integer id);
}
