package com.laptrinhwindows.library_management.controller;

import com.laptrinhwindows.library_management.controller.manager.BookPanelController;
import com.laptrinhwindows.library_management.controller.manager.BookTitlePanelController;
import com.laptrinhwindows.library_management.controller.manager.BorrowOrderPanelController;
import com.laptrinhwindows.library_management.controller.manager.BorrowTrackingPanelController;
import com.laptrinhwindows.library_management.controller.manager.ManagerContext;
import com.laptrinhwindows.library_management.controller.manager.ReportPanelController;
import com.laptrinhwindows.library_management.controller.manager.ReturnBookPanelController;
import com.laptrinhwindows.library_management.controller.manager.StudentPanelController;
import com.laptrinhwindows.library_management.controller.manager.SupplierPanelController;
import com.laptrinhwindows.library_management.dao.UserLookupDao;
import com.laptrinhwindows.library_management.dao.impl.UserLookupDaoImpl;
import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.BookTitle;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.laptrinhwindows.library_management.model.entity.Student;
import com.laptrinhwindows.library_management.model.enumtype.BookStatus;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;
import com.laptrinhwindows.library_management.service.BookService;
import com.laptrinhwindows.library_management.service.BookTitleService;
import com.laptrinhwindows.library_management.service.BorrowOrderService;
import com.laptrinhwindows.library_management.service.StudentService;
import com.laptrinhwindows.library_management.service.SupplierService;
import com.laptrinhwindows.library_management.service.impl.BookServiceImpl;
import com.laptrinhwindows.library_management.service.impl.BookTitleServiceImpl;
import com.laptrinhwindows.library_management.service.impl.BorrowOrderServiceImpl;
import com.laptrinhwindows.library_management.service.impl.StudentServiceImpl;
import com.laptrinhwindows.library_management.service.impl.SupplierServiceImpl;
import com.laptrinhwindows.library_management.view.ManagerFrame;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerController {
    private final ManagerFrame managerFrame;
    private final LoginUserDTO loginUser;
    private final BookService bookService;
    private final BookTitleService bookTitleService;
    private final StudentService studentService;
    private final SupplierService supplierService;
    private final BorrowOrderService borrowOrderService;
    private final UserLookupDao userLookupDao;
    private final StudentPanelController studentPanelController;
    private final BookTitlePanelController bookTitlePanelController;
    private final BookPanelController bookPanelController;
    private final BorrowOrderPanelController borrowOrderPanelController;
    private final ReturnBookPanelController returnBookPanelController;
    private final BorrowTrackingPanelController borrowTrackingPanelController;
    private final SupplierPanelController supplierPanelController;
    private final ReportPanelController reportPanelController;

    public ManagerController(ManagerFrame managerFrame, LoginUserDTO loginUser) {
        this.managerFrame = managerFrame;
        this.loginUser = loginUser;
        this.bookService = new BookServiceImpl();
        this.bookTitleService = new BookTitleServiceImpl();
        this.studentService = new StudentServiceImpl();
        this.supplierService = new SupplierServiceImpl();
        this.borrowOrderService = new BorrowOrderServiceImpl();
        this.userLookupDao = new UserLookupDaoImpl();

        ManagerContext context = new ManagerContext(
                managerFrame,
                loginUser,
                bookService,
                bookTitleService,
                studentService,
                supplierService,
                borrowOrderService,
                userLookupDao,
                this::loadBookTable,
                this::loadBorrowOrderOptions,
                this::loadReturnBorrowOrders,
                this::loadTrackingOrders,
                this::loadDefaultReport
        );

        this.studentPanelController = new StudentPanelController(context);
        this.bookTitlePanelController = new BookTitlePanelController(context);
        this.bookPanelController = new BookPanelController(context);
        this.borrowOrderPanelController = new BorrowOrderPanelController(context);
        this.returnBookPanelController = new ReturnBookPanelController(context);
        this.borrowTrackingPanelController = new BorrowTrackingPanelController(context);
        this.supplierPanelController = new SupplierPanelController(context);
        this.reportPanelController = new ReportPanelController(context);
    }

    public void init() {
        managerFrame.setCurrentUserInfo(loginUser.getUsername(), loginUser.getRoleId(), loginUser.getRoleName());
        managerFrame.setStatusMessage("Đăng nhập thành công với quyền quản lý.");

        studentPanelController.init();
        bookTitlePanelController.init();
        bookPanelController.init();
        borrowOrderPanelController.init();
        returnBookPanelController.init();
        borrowTrackingPanelController.init();
        supplierPanelController.init();
        reportPanelController.init();

        studentPanelController.loadData();
        bookTitlePanelController.loadData();
        loadBookTable();
        loadBorrowOrderOptions();
        loadReturnBorrowOrders();
        loadTrackingOrders();
        supplierPanelController.loadData();
        loadDefaultReport();
    }

    private void loadBookTable() {
        List<Book> books = bookService.getAllBooks();
        List<BookTitle> bookTitles = bookTitleService.getAllBookTitles()
                .stream()
                .filter(bookTitle -> bookTitle.getStatus() == RecordStatus.ACTIVE)
                .collect(Collectors.toList());
        managerFrame.getBookManagementPanel().showBooks(books);
        managerFrame.getBookManagementPanel().setBookTitleOptions(bookTitles);
        managerFrame.getBookManagementPanel().clearBookForm();
    }

    private void loadBorrowOrderOptions() {
        List<Student> students = studentService.getAllStudents();
        List<Book> availableBooks = bookService.getAllBooks()
                .stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
                .filter(book -> book.getBookTitle() != null && book.getBookTitle().getStatus() == RecordStatus.ACTIVE)
                .collect(Collectors.toList());

        managerFrame.getBorrowOrderPanel().showStudents(students);
        managerFrame.getBorrowOrderPanel().showAvailableBooks(availableBooks);
    }

    private void loadReturnBorrowOrders() {
        List<BorrowOrder> borrowOrders = borrowOrderService.searchBorrowingOrders(null, null, null);
        managerFrame.getReturnBookPanel().showBorrowOrders(borrowOrders);
        managerFrame.getReturnBookPanel().clearReturnForm();
    }

    private void loadTrackingOrders() {
        List<BorrowOrder> borrowOrders = borrowOrderService.searchTrackingOrders(null, null, null, null, "BORROWING");
        managerFrame.getBorrowTrackingPanel().showBorrowOrders(borrowOrders);
    }

    private void loadDefaultReport() {
        reportPanelController.loadDefaultReport();
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

    public SupplierService getSupplierService() {
        return supplierService;
    }
}
