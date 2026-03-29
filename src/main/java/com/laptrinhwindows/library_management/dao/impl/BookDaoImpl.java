package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.BookDao;
import com.laptrinhwindows.library_management.model.entity.Book;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BookDaoImpl extends GenericDaoImpl<Book> implements BookDao {

    public BookDaoImpl() {
        super(Book.class);
    }

    // Lấy toàn bộ danh sách cuốn sách kèm đầu sách tương ứng.
    @Override
    public List<Book> findAll() {
        EntityManager em = createEntityManager();
        try {
            String jpql = "select b from Book b join fetch b.bookTitle bt left join fetch bt.supplier order by b.id";
            return em.createQuery(jpql, Book.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Thêm mới một cuốn sách cụ thể.
    @Override
    public Book save(Book book) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return book;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Cập nhật thông tin cuốn sách như vị trí hoặc trạng thái.
    @Override
    public Book update(Book book) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            Book updatedBook = em.merge(book);
            em.getTransaction().commit();
            return updatedBook;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Tìm một cuốn sách theo khóa chính.
    @Override
    public Book findById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            List<Book> results = em.createQuery(
                            "select b from Book b join fetch b.bookTitle bt left join fetch bt.supplier where b.id = :id",
                            Book.class
                    )
                    .setParameter("id", id)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
}
