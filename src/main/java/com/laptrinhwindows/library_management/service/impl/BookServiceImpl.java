package com.laptrinhwindows.library_management.service.impl;

import com.laptrinhwindows.library_management.dao.BookDao;
import com.laptrinhwindows.library_management.dao.impl.BookDaoImpl;
import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl() {
        this.bookDao = new BookDaoImpl();
    }

    // Lấy toàn bộ danh sách cuốn sách.
    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    // Thêm mới một cuốn sách.
    @Override
    public Book addBook(Book book) {
        return bookDao.save(book);
    }

    // Cập nhật thông tin cuốn sách.
    @Override
    public Book updateBook(Book book) {
        return bookDao.update(book);
    }

    // Tìm cuốn sách theo khóa chính.
    @Override
    public Book findBookById(Integer id) {
        return bookDao.findById(id);
    }
}
