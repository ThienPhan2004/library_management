package com.laptrinhwindows.library_management.controller;

import com.laptrinhwindows.library_management.controller.common.LibraryContext;
import com.laptrinhwindows.library_management.controller.manager.BookPanelController;
import com.laptrinhwindows.library_management.controller.manager.BookTitlePanelController;
import com.laptrinhwindows.library_management.controller.manager.ReportPanelController;
import com.laptrinhwindows.library_management.controller.manager.SupplierPanelController;
import com.laptrinhwindows.library_management.controller.shared.StudentPanelController;
import com.laptrinhwindows.library_management.dao.UserLookupDao;
import com.laptrinhwindows.library_management.dao.impl.UserLookupDaoImpl;
import com.laptrinhwindows.library_management.dto.LoginUserDTO;
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
import com.laptrinhwindows.library_management.view.LoginFrame;
import com.laptrinhwindows.library_management.view.ManagerFrame;

public class ManagerController {
    private final ManagerFrame managerFrame;
    private final LoginUserDTO loginUser;
    private final BookTitlePanelController bookTitlePanelController;
    private final BookPanelController bookPanelController;
    private final SupplierPanelController supplierPanelController;
    private final StudentPanelController studentPanelController;
    private final ReportPanelController reportPanelController;

    public ManagerController(ManagerFrame managerFrame, LoginUserDTO loginUser) {
        this.managerFrame = managerFrame;
        this.loginUser = loginUser;

        // Khởi tạo các service mà quản lý được phép thao tác.
        BookService bookService = new BookServiceImpl();
        BookTitleService bookTitleService = new BookTitleServiceImpl();
        StudentService studentService = new StudentServiceImpl();
        SupplierService supplierService = new SupplierServiceImpl();
        BorrowOrderService borrowOrderService = new BorrowOrderServiceImpl();
        UserLookupDao userLookupDao = new UserLookupDaoImpl();

        LibraryContext context = new LibraryContext(
                managerFrame,
                loginUser,
                bookService,
                bookTitleService,
                studentService,
                supplierService,
                borrowOrderService,
                userLookupDao,
                this::reloadBookPanel,
                () -> {
                },
                () -> {
                },
                () -> {
                },
                this::reloadReportPanel
        );

        this.studentPanelController = new StudentPanelController(context);
        this.bookTitlePanelController = new BookTitlePanelController(context);
        this.bookPanelController = new BookPanelController(context);
        this.supplierPanelController = new SupplierPanelController(context);
        this.reportPanelController = new ReportPanelController(context);
    }

    public void init() {
        managerFrame.setCurrentUserInfo(loginUser.getUsername(), loginUser.getRoleId(), loginUser.getRoleName());
        managerFrame.setStatusMessage("Đăng nhập thành công với quyền quản lý thư viện.");
        managerFrame.addLogoutListener(event -> handleLogout());

        // Mỗi tab lớn sẽ có một controller riêng để dễ quản lý.
        studentPanelController.init();
        bookTitlePanelController.init();
        bookPanelController.init();
        supplierPanelController.init();
        reportPanelController.init();

        studentPanelController.loadData();
        bookTitlePanelController.loadData();
        bookPanelController.loadData();
        supplierPanelController.loadData();
        reportPanelController.loadDefaultReport();
    }

    private void reloadBookPanel() {
        // Làm mới lại tab cuốn sách sau khi dữ liệu đầu sách thay đổi.
        if (bookPanelController != null) {
            bookPanelController.loadData();
        }
    }

    private void reloadReportPanel() {
        // Làm mới báo cáo khi dữ liệu mượn trả có thay đổi.
        if (reportPanelController != null) {
            reportPanelController.loadDefaultReport();
        }
    }

    private void handleLogout() {
        managerFrame.dispose();
        LoginFrame loginFrame = new LoginFrame();
        LoginController loginController = new LoginController(loginFrame);
        loginController.init();
        loginFrame.setVisible(true);
    }
}
