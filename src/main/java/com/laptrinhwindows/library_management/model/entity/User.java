package com.laptrinhwindows.library_management.model.entity;

public class User {
    @Entity
    @Table(name = "users")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String username;
        private String password;

        @ManyToOne
        @JoinColumn(name = "role_id")
        private Role role;

        @OneToMany(mappedBy = "user")
        private List<BorrowOrder> orders;
    }
}
