package com.laptrinhwindows.library_management.model.entity;

import jakarta.persistence.*;

public class BorrowDetail {
    @Entity
    @Table(name = "borrow_details")
    public class BorrowDetail {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne
        @JoinColumn(name = "order_id")
        private BorrowOrder order;

        @ManyToOne
        @JoinColumn(name = "book_id")
        private Book book;
    }
}
