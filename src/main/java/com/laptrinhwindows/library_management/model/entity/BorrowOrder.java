package com.laptrinhwindows.library_management.model.entity;

import jakarta.persistence.*;

public class BorrowOrder {
    @Entity
    @Table(name = "borrow_orders")
    public class BorrowOrder {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private java.time.LocalDate borrowDate;
        private java.time.LocalDate dueDate;

        @Enumerated(EnumType.STRING)
        private OrderStatus status;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "student_id")
        private Student student;

        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
        private List<BorrowDetail> details;

        @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
        private ReturnRecord returnRecord;
    }
}
