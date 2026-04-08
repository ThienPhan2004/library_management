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

    @Override
    public List<User> findAll() {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select u from User u left join fetch u.role order by u.id";
            return em.createQuery(jpql, User.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> search(String username, Integer roleId) {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select u from User u left join fetch u.role "
                    + "where (:username = '' or lower(u.username) like lower(:username)) "
                    + "and (:roleId is null or u.role.id = :roleId) "
                    + "order by u.id";

            return em.createQuery(jpql, User.class)
                    .setParameter("username", "%" + username.trim() + "%")
                    .setParameter("roleId", roleId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public User save(User user) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public User update(User user) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            User updatedUser = em.merge(user);
            em.getTransaction().commit();
            return updatedUser;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public User findById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select u from User u left join fetch u.role where u.id = :id";
            List<User> users = em.createQuery(jpql, User.class)
                    .setParameter("id", id)
                    .getResultList();
            return users.isEmpty() ? null : users.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select count(u) from User u where lower(u.username) = lower(:username)";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("username", username.trim())
                    .getSingleResult();
            return count != null && count > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByUsernameExceptId(String username, Integer id) {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select count(u) from User u "
                    + "where lower(u.username) = lower(:username) and u.id <> :id";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("username", username.trim())
                    .setParameter("id", id)
                    .getSingleResult();
            return count != null && count > 0;
        } finally {
            em.close();
        }
    }
}
