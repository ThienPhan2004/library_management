package com.laptrinhwindows.library_management.service;

import com.laptrinhwindows.library_management.model.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book addBook(Book book);

    Book updateBook(Book book);

    Book findBookById(Integer id);
}
