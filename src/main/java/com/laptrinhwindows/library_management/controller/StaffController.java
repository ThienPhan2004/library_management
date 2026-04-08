package com.laptrinhwindows.library_management.controller;

import com.laptrinhwindows.library_management.controller.common.LibraryContext;
import com.laptrinhwindows.library_management.controller.shared.StudentPanelController;
import com.laptrinhwindows.library_management.controller.staff.BorrowOrderPanelController;
import com.laptrinhwindows.library_management.controller.staff.BorrowTrackingPanelController;
import com.laptrinhwindows.library_management.controller.staff.ReturnBookPanelController;
import com.laptrinhwindows.library_management.dao.UserLookupDao;
import com.laptrinhwindows.library_management.dao.impl.UserLookupDaoImpl;
import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.laptrinhwindows.library_management.model.entity.Student;
import com.laptrinhwindows.library_management.model.enumtype.BookStatus;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;
import com.laptrinhwindows.library_management.service.BookService;
import com.laptrinhwindows.library_management.service.BorrowOrderService;
import com.laptrinhwindows.library_management.service.StudentService;
import com.laptrinhwindows.library_management.service.impl.BookServiceImpl;
import com.laptrinhwindows.library_management.service.impl.BorrowOrderServiceImpl;
import com.laptrinhwindows.library_management.service.impl.StudentServiceImpl;
import com.laptrinhwindows.library_management.view.LoginFrame;
import com.laptrinhwindows.library_management.view.StaffFrame;

import java.util.List;
import java.util.stream.Collectors;

public class StaffController {
    private final StaffFrame staffFrame;
    private final LoginUserDTO loginUser;
    private final BookService bookService;
    private final StudentService studentService;
    private final BorrowOrderService borrowOrderService;
    private final StudentPanelController studentPanelController;
    private final BorrowOrderPanelController borrowOrderPanelController;
    private final ReturnBookPanelController returnBookPanelController;
    private final BorrowTrackingPanelController borrowTrackingPanelController;

    public StaffController(StaffFrame staffFrame, LoginUserDTO loginUser) {
        this.staffFrame = staffFrame;
        this.loginUser = loginUser;
        // Nhân viên làm việc chủ yếu với mượn, trả và tra cứu sinh viên.
        this.bookService = new BookServiceImpl();
        this.studentService = new StudentServiceImpl();
        this.borrowOrderService = new BorrowOrderServiceImpl();
        UserLookupDao userLookupDao = new UserLookupDaoImpl();

        LibraryContext context = new LibraryContext(
                staffFrame,
                loginUser,
                bookService,
                null,
                studentService,
                null,
                borrowOrderService,
                userLookupDao,
                () -> {
                },
                this::loadBorrowOrderOptions,
                this::loadReturnBorrowOrders,
                this::loadTrackingOrders,
                () -> {
                }
        );

        this.studentPanelController = new StudentPanelController(context);
        this.borrowOrderPanelController = new BorrowOrderPanelController(context);
        this.returnBookPanelController = new ReturnBookPanelController(context);
        this.borrowTrackingPanelController = new BorrowTrackingPanelController(context);
    }

    public void init() {
        staffFrame.setCurrentUserInfo(loginUser.getUsername(), loginUser.getRoleId(), loginUser.getRoleName());
        staffFrame.setStatusMessage("Đăng nhập thành công với quyền nhân viên thư viện.");
        staffFrame.addLogoutListener(event -> handleLogout());

        // Khởi tạo các controller cho những tab nhân viên sử dụng.
        studentPanelController.init();
        borrowOrderPanelController.init();
        returnBookPanelController.init();
        borrowTrackingPanelController.init();

        studentPanelController.loadData();
        loadBorrowOrderOptions();
        loadReturnBorrowOrders();
        loadTrackingOrders();
    }

    private void loadBorrowOrderOptions() {
        // Chỉ hiển thị những cuốn sách đang sẵn sàng cho mượn.
        List<Student> students = studentService.getAllStudents();
        List<Book> availableBooks = bookService.getAllBooks()
                .stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
                .filter(book -> book.getBookTitle() != null && book.getBookTitle().getStatus() == RecordStatus.ACTIVE)
                .collect(Collectors.toList());

        staffFrame.getBorrowOrderPanel().showStudents(students);
        staffFrame.getBorrowOrderPanel().showAvailableBooks(availableBooks);
    }

    private void loadReturnBorrowOrders() {
        List<BorrowOrder> borrowOrders = borrowOrderService.searchBorrowingOrders(null, null, null);
        staffFrame.getReturnBookPanel().showBorrowOrders(borrowOrders);
        staffFrame.getReturnBookPanel().clearReturnForm();
    }

    private void loadTrackingOrders() {
        List<BorrowOrder> borrowOrders = borrowOrderService.searchTrackingOrders(null, null, null, null, "ALL");
        staffFrame.getBorrowTrackingPanel().showBorrowOrders(borrowOrders);
    }

    private void handleLogout() {
        staffFrame.dispose();
        LoginFrame loginFrame = new LoginFrame();
        LoginController loginController = new LoginController(loginFrame);
        loginController.init();
        loginFrame.setVisible(true);
    }
}
