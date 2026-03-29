package com.laptrinhwindows.library_management.dao;

import com.laptrinhwindows.library_management.model.entity.BookTitle;

import java.util.List;

public interface BookTitleDao {
    List<BookTitle> findAll();

    List<BookTitle> search(String title, String author, String category);

    BookTitle save(BookTitle bookTitle);

    BookTitle update(BookTitle bookTitle);

    void deleteById(Integer id);

    BookTitle findById(Integer id);
}
