package com.laptrinhwindows.library_management.model.entity;

import jakarta.persistence.*;

public class Book {
    @Entity
    @Table(name = "books")
    public class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String location;

        @Enumerated(EnumType.STRING)
        private BookStatus status;

        @ManyToOne
        @JoinColumn(name = "title_id")
        private BookTitle bookTitle;

        @OneToMany(mappedBy = "book")
        private List<BorrowDetail> borrowDetails;
    }
}
