package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.StudentDao;
import com.laptrinhwindows.library_management.model.entity.Student;
import jakarta.persistence.EntityManager;

import java.util.List;

public class StudentDaoImpl extends GenericDaoImpl<Student> implements StudentDao {

    public StudentDaoImpl() {
        super(Student.class);
    }

    // Lấy toàn bộ danh sách học sinh để hiển thị lên bảng.
    @Override
    public List<Student> findAll() {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select s from Student s order by s.id";
            return em.createQuery(jpql, Student.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm kiếm học sinh theo mã, tên hoặc lớp.
    @Override
    public List<Student> search(String studentCode, String fullName, String className) {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select s from Student s "
                    + "where (:studentCode = '' or lower(s.studentCode) like lower(:studentCode)) "
                    + "and (:fullName = '' or lower(s.fullName) like lower(:fullName)) "
                    + "and (:className = '' or lower(s.className) like lower(:className)) "
                    + "order by s.id";

            return em.createQuery(jpql, Student.class)
                    .setParameter("studentCode", "%" + studentCode.trim() + "%")
                    .setParameter("fullName", "%" + fullName.trim() + "%")
                    .setParameter("className", "%" + className.trim() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Thêm mới một học sinh vào cơ sở dữ liệu.
    @Override
    public Student save(Student student) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            return student;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Cập nhật thông tin học sinh đã tồn tại.
    @Override
    public Student update(Student student) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            Student updatedStudent = em.merge(student);
            em.getTransaction().commit();
            return updatedStudent;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Xóa học sinh theo mã định danh.
    @Override
    public void deleteById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
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

    // Tìm một học sinh theo khóa chính.
    @Override
    public Student findById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }
}
