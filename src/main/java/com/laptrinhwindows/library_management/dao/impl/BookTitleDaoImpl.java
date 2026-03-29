package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.BookTitleDao;
import com.laptrinhwindows.library_management.model.entity.BookTitle;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BookTitleDaoImpl extends GenericDaoImpl<BookTitle> implements BookTitleDao {

    public BookTitleDaoImpl() {
        super(BookTitle.class);
    }

    // Lấy toàn bộ danh sách đầu sách để hiển thị lên bảng.
    @Override
    public List<BookTitle> findAll() {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select bt from BookTitle bt left join fetch bt.supplier order by bt.status asc, bt.id";
            return em.createQuery(jpql, BookTitle.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm kiếm đầu sách theo tên sách, tác giả và thể loại.
    @Override
    public List<BookTitle> search(String title, String author, String category) {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select bt from BookTitle bt left join fetch bt.supplier "
                    + "where (:title = '' or lower(bt.title) like lower(:title)) "
                    + "and (:author = '' or lower(bt.author) like lower(:author)) "
                    + "and (:category = '' or lower(bt.category) like lower(:category)) "
                    + "order by bt.status asc, bt.id";

            return em.createQuery(jpql, BookTitle.class)
                    .setParameter("title", "%" + title.trim() + "%")
                    .setParameter("author", "%" + author.trim() + "%")
                    .setParameter("category", "%" + category.trim() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Thêm mới một đầu sách.
    @Override
    public BookTitle save(BookTitle bookTitle) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(bookTitle);
            em.getTransaction().commit();
            return bookTitle;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Cập nhật thông tin đầu sách.
    @Override
    public BookTitle update(BookTitle bookTitle) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            BookTitle updatedBookTitle = em.merge(bookTitle);
            em.getTransaction().commit();
            return updatedBookTitle;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Xóa đầu sách theo mã định danh.
    @Override
    public void deleteById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            BookTitle bookTitle = em.find(BookTitle.class, id);
            if (bookTitle != null) {
                bookTitle.setStatus(RecordStatus.INACTIVE);
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

    // Tìm một đầu sách theo khóa chính.
    @Override
    public BookTitle findById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            List<BookTitle> results = em.createQuery(
                            "select bt from BookTitle bt left join fetch bt.supplier where bt.id = :id",
                            BookTitle.class
                    )
                    .setParameter("id", id)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
}
