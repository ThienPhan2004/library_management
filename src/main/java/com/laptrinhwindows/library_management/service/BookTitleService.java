package com.laptrinhwindows.library_management.service;

import com.laptrinhwindows.library_management.model.entity.BookTitle;

import java.util.List;

public interface BookTitleService {
    List<BookTitle> getAllBookTitles();

    List<BookTitle> searchBookTitles(String title, String author, String category);

    BookTitle addBookTitle(BookTitle bookTitle);

    BookTitle updateBookTitle(BookTitle bookTitle);

    void deleteBookTitle(Integer id);

    BookTitle findBookTitleById(Integer id);
}
