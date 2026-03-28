package com.laptrinhwindows.library_management.controller;

import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.service.AuthService;
import com.laptrinhwindows.library_management.service.impl.AuthServiceImpl;
import com.laptrinhwindows.library_management.view.LoginFrame;
import com.laptrinhwindows.library_management.view.MainFrame;

public class LoginController {
    private final LoginFrame loginFrame;
    private final AuthService authService;

    public LoginController(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.authService = new AuthServiceImpl();
    }

    // Gắn sự kiện đăng nhập cho form.
    public void init() {
        loginFrame.addLoginListener(event -> handleLogin());
    }

    // Xử lý nghiệp vụ đăng nhập và mở màn hình chính nếu thành công.
    private void handleLogin() {
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

        MainFrame mainFrame = new MainFrame();
        MainController mainController = new MainController(mainFrame, loginUser);
        mainController.init();

        loginFrame.dispose();
        mainFrame.setVisible(true);
    }
}
