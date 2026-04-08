package com.laptrinhwindows.library_management.controller.common;

import com.laptrinhwindows.library_management.dao.UserLookupDao;
import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.service.BookService;
import com.laptrinhwindows.library_management.service.BookTitleService;
import com.laptrinhwindows.library_management.service.BorrowOrderService;
import com.laptrinhwindows.library_management.service.StudentService;
import com.laptrinhwindows.library_management.service.SupplierService;
import com.laptrinhwindows.library_management.view.LibraryWorkspaceFrame;
import com.laptrinhwindows.library_management.view.manager.BookManagementPanel;
import com.laptrinhwindows.library_management.view.manager.BookTitleManagementPanel;
import com.laptrinhwindows.library_management.view.manager.ReportPanel;
import com.laptrinhwindows.library_management.view.manager.SupplierManagementPanel;
import com.laptrinhwindows.library_management.view.shared.StudentManagementPanel;
import com.laptrinhwindows.library_management.view.staff.BorrowOrderPanel;
import com.laptrinhwindows.library_management.view.staff.BorrowTrackingPanel;
import com.laptrinhwindows.library_management.view.staff.ReturnBookPanel;

public class LibraryContext {
    private final LibraryWorkspaceFrame workspaceFrame;
    private final LoginUserDTO loginUser;
    private final BookService bookService;
    private final BookTitleService bookTitleService;
    private final StudentService studentService;
    private final SupplierService supplierService;
    private final BorrowOrderService borrowOrderService;
    private final UserLookupDao userLookupDao;
    private final Runnable refreshBookTable;
    private final Runnable refreshBorrowOrderOptions;
    private final Runnable refreshReturnBorrowOrders;
    private final Runnable refreshTrackingOrders;
    private final Runnable refreshDefaultReport;

    public LibraryContext(
            LibraryWorkspaceFrame workspaceFrame,
            LoginUserDTO loginUser,
            BookService bookService,
            BookTitleService bookTitleService,
            StudentService studentService,
            SupplierService supplierService,
            BorrowOrderService borrowOrderService,
            UserLookupDao userLookupDao,
            Runnable refreshBookTable,
            Runnable refreshBorrowOrderOptions,
            Runnable refreshReturnBorrowOrders,
            Runnable refreshTrackingOrders,
            Runnable refreshDefaultReport
    ) {
        this.workspaceFrame = workspaceFrame;
        this.loginUser = loginUser;
        this.bookService = bookService;
        this.bookTitleService = bookTitleService;
        this.studentService = studentService;
        this.supplierService = supplierService;
        this.borrowOrderService = borrowOrderService;
        this.userLookupDao = userLookupDao;
        this.refreshBookTable = refreshBookTable;
        this.refreshBorrowOrderOptions = refreshBorrowOrderOptions;
        this.refreshReturnBorrowOrders = refreshReturnBorrowOrders;
        this.refreshTrackingOrders = refreshTrackingOrders;
        this.refreshDefaultReport = refreshDefaultReport;
    }

    public LoginUserDTO getLoginUser() {
        return loginUser;
    }

    public BookService getBookService() {
        return bookService;
    }

    public BookTitleService getBookTitleService() {
        return bookTitleService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public SupplierService getSupplierService() {
        return supplierService;
    }

    public BorrowOrderService getBorrowOrderService() {
        return borrowOrderService;
    }

    public UserLookupDao getUserLookupDao() {
        return userLookupDao;
    }

    public StudentManagementPanel getStudentManagementPanel() {
        return workspaceFrame.getStudentManagementPanel();
    }

    public BookTitleManagementPanel getBookTitleManagementPanel() {
        return workspaceFrame.getBookTitleManagementPanel();
    }

    public BookManagementPanel getBookManagementPanel() {
        return workspaceFrame.getBookManagementPanel();
    }

    public BorrowOrderPanel getBorrowOrderPanel() {
        return workspaceFrame.getBorrowOrderPanel();
    }

    public ReturnBookPanel getReturnBookPanel() {
        return workspaceFrame.getReturnBookPanel();
    }

    public BorrowTrackingPanel getBorrowTrackingPanel() {
        return workspaceFrame.getBorrowTrackingPanel();
    }

    public SupplierManagementPanel getSupplierManagementPanel() {
        return workspaceFrame.getSupplierManagementPanel();
    }

    public ReportPanel getReportPanel() {
        return workspaceFrame.getReportPanel();
    }

    public void setStatusMessage(String message) {
        workspaceFrame.setStatusMessage(message);
    }

    public void refreshBookTable() {
        refreshBookTable.run();
    }

    public void refreshBorrowOrderOptions() {
        refreshBorrowOrderOptions.run();
    }

    public void refreshReturnBorrowOrders() {
        refreshReturnBorrowOrders.run();
    }

    public void refreshTrackingOrders() {
        refreshTrackingOrders.run();
    }

    public void refreshDefaultReport() {
        refreshDefaultReport.run();
    }

    public void refreshCirculationViews() {
        refreshBookTable();
        refreshBorrowOrderOptions();
        refreshReturnBorrowOrders();
        refreshTrackingOrders();
        refreshDefaultReport();
    }
}
