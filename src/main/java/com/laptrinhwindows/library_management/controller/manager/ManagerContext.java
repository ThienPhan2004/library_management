package com.laptrinhwindows.library_management.controller.manager;

import com.laptrinhwindows.library_management.dao.UserLookupDao;
import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.service.BookService;
import com.laptrinhwindows.library_management.service.BookTitleService;
import com.laptrinhwindows.library_management.service.BorrowOrderService;
import com.laptrinhwindows.library_management.service.StudentService;
import com.laptrinhwindows.library_management.service.SupplierService;
import com.laptrinhwindows.library_management.view.ManagerFrame;
import com.laptrinhwindows.library_management.view.manager.BookManagementPanel;
import com.laptrinhwindows.library_management.view.manager.BookTitleManagementPanel;
import com.laptrinhwindows.library_management.view.manager.BorrowOrderPanel;
import com.laptrinhwindows.library_management.view.manager.BorrowTrackingPanel;
import com.laptrinhwindows.library_management.view.manager.ReportPanel;
import com.laptrinhwindows.library_management.view.manager.ReturnBookPanel;
import com.laptrinhwindows.library_management.view.manager.StudentManagementPanel;
import com.laptrinhwindows.library_management.view.manager.SupplierManagementPanel;

public class ManagerContext {
    private final ManagerFrame managerFrame;
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

    public ManagerContext(
            ManagerFrame managerFrame,
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
        this.managerFrame = managerFrame;
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
        return managerFrame.getStudentManagementPanel();
    }

    public BookTitleManagementPanel getBookTitleManagementPanel() {
        return managerFrame.getBookTitleManagementPanel();
    }

    public BookManagementPanel getBookManagementPanel() {
        return managerFrame.getBookManagementPanel();
    }

    public BorrowOrderPanel getBorrowOrderPanel() {
        return managerFrame.getBorrowOrderPanel();
    }

    public ReturnBookPanel getReturnBookPanel() {
        return managerFrame.getReturnBookPanel();
    }

    public BorrowTrackingPanel getBorrowTrackingPanel() {
        return managerFrame.getBorrowTrackingPanel();
    }

    public SupplierManagementPanel getSupplierManagementPanel() {
        return managerFrame.getSupplierManagementPanel();
    }

    public ReportPanel getReportPanel() {
        return managerFrame.getReportPanel();
    }

    public void setStatusMessage(String message) {
        managerFrame.setStatusMessage(message);
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
