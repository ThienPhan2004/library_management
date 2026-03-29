package com.laptrinhwindows.library_management.dao;

import com.laptrinhwindows.library_management.model.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();

    Book save(Book book);

    Book update(Book book);

    Book findById(Integer id);
}
