package com.laptrinhwindows.library_management.model.entity;

import jakarta.persistence.*;

public class Supplier {
    @Entity
    @Table(name = "suppliers")
    public class Supplier {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String name;
        private String phone;
        private String address;
        private String email;

        @OneToMany(mappedBy = "supplier")
        private List<BookTitle> bookTitles;
    }
}
