package com.laptrinhwindows.library_management.controller;

import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.service.BookService;
import com.laptrinhwindows.library_management.service.BorrowOrderService;
import com.laptrinhwindows.library_management.service.StudentService;
import com.laptrinhwindows.library_management.service.impl.BookServiceImpl;
import com.laptrinhwindows.library_management.service.impl.BorrowOrderServiceImpl;
import com.laptrinhwindows.library_management.service.impl.StudentServiceImpl;
import com.laptrinhwindows.library_management.view.MainFrame;

public class MainController {
    private final MainFrame mainFrame;
    private final LoginUserDTO loginUser;
    private final BookService bookService;
    private final StudentService studentService;
    private final BorrowOrderService borrowOrderService;

    public MainController(MainFrame mainFrame, LoginUserDTO loginUser) {
        this.mainFrame = mainFrame;
        this.loginUser = loginUser;
        this.bookService = new BookServiceImpl();
        this.studentService = new StudentServiceImpl();
        this.borrowOrderService = new BorrowOrderServiceImpl();
    }

    // Khởi tạo controller và nối với giao diện.
    public void init() {
        mainFrame.setCurrentUserInfo(loginUser.getUsername(), loginUser.getRoleId(), loginUser.getRoleName());
        mainFrame.setStatusMessage("Đăng nhập thành công.");
    }

    public BookService getBookService() {
        return bookService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public BorrowOrderService getBorrowOrderService() {
        return borrowOrderService;
    }
}
