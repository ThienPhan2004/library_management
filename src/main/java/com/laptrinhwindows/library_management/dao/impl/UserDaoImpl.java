package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.UserDao;
import com.laptrinhwindows.library_management.model.entity.User;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    // Tìm người dùng theo tên đăng nhập và mật khẩu để phục vụ màn hình đăng nhập.
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select u from User u join fetch u.role "
                    + "where u.username = :username and u.password = :password";

            List<User> users = em.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();

            return users.isEmpty() ? null : users.get(0);
        } finally {
            em.close();
        }
    }
}
