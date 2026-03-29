package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.SupplierDao;
import com.laptrinhwindows.library_management.model.entity.Supplier;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class SupplierDaoImpl extends GenericDaoImpl<Supplier> implements SupplierDao {

    public SupplierDaoImpl() {
        super(Supplier.class);
    }

    // Lấy toàn bộ nhà cung cấp để hiển thị lên bảng.
    @Override
    public List<Supplier> findAll() {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery(
                            "select s from Supplier s order by s.status asc, s.id desc",
                            Supplier.class
                    )
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm nhà cung cấp theo tên, số điện thoại hoặc email.
    @Override
    public List<Supplier> search(String name, String phone, String email) {
        EntityManager em = createEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("select s from Supplier s where 1 = 1");

            if (name != null && !name.isBlank()) {
                jpql.append(" and lower(s.name) like :name");
            }
            if (phone != null && !phone.isBlank()) {
                jpql.append(" and lower(s.phone) like :phone");
            }
            if (email != null && !email.isBlank()) {
                jpql.append(" and lower(s.email) like :email");
            }

            jpql.append(" order by s.status asc, s.id desc");

            TypedQuery<Supplier> query = em.createQuery(jpql.toString(), Supplier.class);
            if (name != null && !name.isBlank()) {
                query.setParameter("name", "%" + name.toLowerCase() + "%");
            }
            if (phone != null && !phone.isBlank()) {
                query.setParameter("phone", "%" + phone.toLowerCase() + "%");
            }
            if (email != null && !email.isBlank()) {
                query.setParameter("email", "%" + email.toLowerCase() + "%");
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm một nhà cung cấp theo mã định danh.
    @Override
    public Supplier findById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            return em.find(Supplier.class, id);
        } finally {
            em.close();
        }
    }

    // Lưu nhà cung cấp mới vào cơ sở dữ liệu.
    @Override
    public Supplier save(Supplier supplier) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(supplier);
            em.getTransaction().commit();
            return supplier;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Cập nhật thông tin nhà cung cấp đã có.
    @Override
    public Supplier update(Supplier supplier) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            Supplier mergedSupplier = em.merge(supplier);
            em.getTransaction().commit();
            return mergedSupplier;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Xóa nhà cung cấp theo mã định danh.
    @Override
    public void deleteById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            Supplier supplier = em.find(Supplier.class, id);
            if (supplier != null) {
                supplier.setStatus(RecordStatus.INACTIVE);
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
}
