package com.laptrinhwindows.library_management.model.entity;

import jakarta.persistence.*;

public class ReturnRecord {
    @Entity
    @Table(name = "returns")
    public class ReturnRecord {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private java.time.LocalDate returnDate;

        @OneToOne
        @JoinColumn(name = "order_id")
        private BorrowOrder order;
    }
}
