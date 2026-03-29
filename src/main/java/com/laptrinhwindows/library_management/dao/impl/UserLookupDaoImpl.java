package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.UserLookupDao;
import com.laptrinhwindows.library_management.model.entity.User;
import jakarta.persistence.EntityManager;

public class UserLookupDaoImpl extends GenericDaoImpl<User> implements UserLookupDao {

    public UserLookupDaoImpl() {
        super(User.class);
    }

    // Tìm người dùng theo khóa chính để gán vào phiếu mượn.
    @Override
    public User findById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }
}
