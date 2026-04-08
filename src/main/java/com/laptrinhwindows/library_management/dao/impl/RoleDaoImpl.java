package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.RoleDao;
import com.laptrinhwindows.library_management.model.entity.Role;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {

    public RoleDaoImpl() {
        super(Role.class);
    }

    @Override
    public List<Role> findAll() {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select r from Role r order by r.id";
            return em.createQuery(jpql, Role.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Role findById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            return em.find(Role.class, id);
        } finally {
            em.close();
        }
    }
}
