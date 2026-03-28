package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.config.JpaUtil;
import jakarta.persistence.EntityManager;

public abstract class GenericDaoImpl<T> {
    private final Class<T> entityClass;

    protected GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager createEntityManager() {
        return JpaUtil.getEntityManager();
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }
}
