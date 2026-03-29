package com.laptrinhwindows.library_management.controller;

import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.service.BookService;
import com.laptrinhwindows.library_management.service.BorrowOrderService;
import com.laptrinhwindows.library_management.service.StudentService;
import com.laptrinhwindows.library_management.service.impl.BookServiceImpl;
import com.laptrinhwindows.library_management.service.impl.BorrowOrderServiceImpl;
import com.laptrinhwindows.library_management.service.impl.StudentServiceImpl;
import com.laptrinhwindows.library_management.view.AdminFrame;

public class AdminController {
    private final AdminFrame adminFrame;
    private final LoginUserDTO loginUser;
    private final BookService bookService;
    private final StudentService studentService;
    private final BorrowOrderService borrowOrderService;

    public AdminController(AdminFrame adminFrame, LoginUserDTO loginUser) {
        this.adminFrame = adminFrame;
        this.loginUser = loginUser;
        this.bookService = new BookServiceImpl();
        this.studentService = new StudentServiceImpl();
        this.borrowOrderService = new BorrowOrderServiceImpl();
    }

    // Khởi tạo giao diện dành cho quản trị viên.
    public void init() {
        adminFrame.setCurrentUserInfo(loginUser.getUsername(), loginUser.getRoleId(), loginUser.getRoleName());
        adminFrame.setStatusMessage("Đăng nhập thành công với quyền quản trị viên.");
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
