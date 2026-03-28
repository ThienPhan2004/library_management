package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.BookDao;
import com.laptrinhwindows.library_management.model.entity.Book;

public class BookDaoImpl extends GenericDaoImpl<Book> implements BookDao {

    public BookDaoImpl() {
        super(Book.class);
    }
}
