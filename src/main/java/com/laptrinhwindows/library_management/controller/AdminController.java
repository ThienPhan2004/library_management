package com.laptrinhwindows.library_management.controller;

import com.laptrinhwindows.library_management.controller.admin.AccountManagementController;
import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.service.UserService;
import com.laptrinhwindows.library_management.service.impl.UserServiceImpl;
import com.laptrinhwindows.library_management.view.AdminFrame;
import com.laptrinhwindows.library_management.view.LoginFrame;

public class AdminController {
    private final AdminFrame adminFrame;
    private final LoginUserDTO loginUser;
    private final AccountManagementController accountManagementController;

    public AdminController(AdminFrame adminFrame, LoginUserDTO loginUser) {
        this.adminFrame = adminFrame;
        this.loginUser = loginUser;

        // Admin chỉ quản lý tài khoản người dùng trong hệ thống.
        UserService userService = new UserServiceImpl();
        this.accountManagementController = new AccountManagementController(adminFrame, loginUser, userService);
    }

    public void init() {
        adminFrame.setCurrentUserInfo(loginUser.getUsername(), loginUser.getRoleId(), loginUser.getRoleName());
        adminFrame.setStatusMessage("Đăng nhập thành công với quyền quản trị tài khoản.");
        adminFrame.addLogoutListener(event -> handleLogout());

        accountManagementController.init();
        accountManagementController.loadData();
    }

    private void handleLogout() {
        // Đăng xuất: đóng frame hiện tại và quay về màn hình đăng nhập.
        adminFrame.dispose();
        LoginFrame loginFrame = new LoginFrame();
        LoginController loginController = new LoginController(loginFrame);
        loginController.init();
        loginFrame.setVisible(true);
    }
}
