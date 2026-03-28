package com.laptrinhwindows.library_management.model.entity;

import jakarta.persistence.*;

public class Student {
    @Entity
    @Table(name = "students")
    public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "student_code", unique = true)
        private String studentCode;

        private String fullName;
        private String className;
        private String phone;

        @OneToMany(mappedBy = "student")
        private List<BorrowOrder> orders;
    }
}
