package com.laptrinhwindows.library_management.model.entity;

import jakarta.persistence.*;

public class BookTitle {
    @Entity
    @Table(name = "book_titles")
    public class BookTitle {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String title;
        private String author;
        private String category;
        private Integer publishYear;

        @ManyToOne
        @JoinColumn(name = "supplier_id")
        private Supplier supplier;

        @OneToMany(mappedBy = "bookTitle")
        private List<Book> books;
    }
}
