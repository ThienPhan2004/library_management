package com.laptrinhwindows.library_management;

import com.laptrinhwindows.library_management.controller.LoginController;
import com.laptrinhwindows.library_management.view.LoginFrame;

import javax.swing.SwingUtilities;

public class MainApp {

    // Nơi khởi tạo chương trình .
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            LoginController loginController = new LoginController(loginFrame);
            loginController.init();
            loginFrame.setVisible(true);
        });
    }
}
