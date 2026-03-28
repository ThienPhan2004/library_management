package com.laptrinhwindows.library_management;

import com.laptrinhwindows.library_management.config.JpaUtil;
import com.laptrinhwindows.library_management.model.entity.*;
import com.laptrinhwindows.library_management.model.enumtype.BookStatus;

import jakarta.persistence.EntityManager;

public class TestJpa {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // 1. Tạo supplier
            Supplier supplier = new Supplier();
            supplier.setName("NXB Test");

            em.persist(supplier);

            // 2. Tạo book title
            BookTitle title = new BookTitle();
            title.setTitle("Test Book");
            title.setSupplier(supplier);

            em.persist(title);

            // 3. Tạo book
            Book book = new Book();
            book.setBookTitle(title);
            book.setStatus(BookStatus.AVAILABLE);

            em.persist(book);

            em.getTransaction().commit();

            System.out.println("✅ Insert thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}