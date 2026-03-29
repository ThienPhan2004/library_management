package com.laptrinhwindows.library_management.service.impl;

import com.laptrinhwindows.library_management.dao.BookTitleDao;
import com.laptrinhwindows.library_management.dao.impl.BookTitleDaoImpl;
import com.laptrinhwindows.library_management.model.entity.BookTitle;
import com.laptrinhwindows.library_management.service.BookTitleService;

import java.util.List;

public class BookTitleServiceImpl implements BookTitleService {
    private final BookTitleDao bookTitleDao;

    public BookTitleServiceImpl() {
        this.bookTitleDao = new BookTitleDaoImpl();
    }

    // Lấy toàn bộ đầu sách.
    @Override
    public List<BookTitle> getAllBookTitles() {
        return bookTitleDao.findAll();
    }

    // Tìm kiếm đầu sách theo nhiều tiêu chí.
    @Override
    public List<BookTitle> searchBookTitles(String title, String author, String category) {
        return bookTitleDao.search(title, author, category);
    }

    // Thêm đầu sách mới.
    @Override
    public BookTitle addBookTitle(BookTitle bookTitle) {
        return bookTitleDao.save(bookTitle);
    }

    // Cập nhật đầu sách.
    @Override
    public BookTitle updateBookTitle(BookTitle bookTitle) {
        return bookTitleDao.update(bookTitle);
    }

    // Xóa đầu sách theo mã định danh.
    @Override
    public void deleteBookTitle(Integer id) {
        bookTitleDao.deleteById(id);
    }

    // Tìm đầu sách theo khóa chính.
    @Override
    public BookTitle findBookTitleById(Integer id) {
        return bookTitleDao.findById(id);
    }
}
