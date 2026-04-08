package com.laptrinhwindows.library_management.controller;

import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.service.AuthService;
import com.laptrinhwindows.library_management.service.impl.AuthServiceImpl;
import com.laptrinhwindows.library_management.view.AdminFrame;
import com.laptrinhwindows.library_management.view.LoginFrame;
import com.laptrinhwindows.library_management.view.ManagerFrame;
import com.laptrinhwindows.library_management.view.StaffFrame;

public class LoginController {
    private final LoginFrame loginFrame;
    private final AuthService authService;

    public LoginController(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.authService = new AuthServiceImpl();
    }

    public void init() {
        // Gắn sự kiện cho nút đăng nhập trên form.
        loginFrame.addLoginListener(event -> handleLogin());
    }

    private void handleLogin() {
        // Lấy dữ liệu người dùng nhập từ giao diện.
        String username = loginFrame.getUsername();
        String password = loginFrame.getPassword();

        if (username.isBlank() || password.isBlank()) {
            loginFrame.setMessage("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            return;
        }

        LoginUserDTO loginUser = authService.login(username, password);
        if (loginUser == null) {
            loginFrame.setMessage("Sai tên đăng nhập hoặc mật khẩu.");
            loginFrame.clearPassword();
            return;
        }

        openRoleScreen(loginUser);
    }

    private void openRoleScreen(LoginUserDTO loginUser) {
        // Mỗi vai trò sẽ được mở một giao diện riêng.
        loginFrame.dispose();

        if (loginUser.getRoleId() != null && loginUser.getRoleId() == 1) {
            AdminFrame adminFrame = new AdminFrame();
            AdminController adminController = new AdminController(adminFrame, loginUser);
            adminController.init();
            adminFrame.setVisible(true);
            return;
        }

        if (loginUser.getRoleId() != null && loginUser.getRoleId() == 2) {
            ManagerFrame managerFrame = new ManagerFrame();
            ManagerController managerController = new ManagerController(managerFrame, loginUser);
            managerController.init();
            managerFrame.setVisible(true);
            return;
        }

        if (loginUser.getRoleId() != null && loginUser.getRoleId() == 3) {
            StaffFrame staffFrame = new StaffFrame();
            StaffController staffController = new StaffController(staffFrame, loginUser);
            staffController.init();
            staffFrame.setVisible(true);
            return;
        }

        loginFrame.setVisible(true);
        loginFrame.setMessage("Tài khoản chưa được gán vai trò hợp lệ.");
    }
}
