package com.laptrinhwindows.library_management.service.impl;

import com.laptrinhwindows.library_management.dao.BookDao;
import com.laptrinhwindows.library_management.dao.impl.BookDaoImpl;
import com.laptrinhwindows.library_management.service.BookService;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl() {
        this.bookDao = new BookDaoImpl();
    }

    public BookDao getBookDao() {
        return bookDao;
    }
}
