package com.laptrinhwindows.library_management.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminFrame extends JFrame {
    private final JLabel titleLabel;
    private final JLabel userInfoLabel;
    private final JLabel statusLabel;

    public AdminFrame() {
        setTitle("Quản lý thư viện - Admin");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("GIAO DIỆN QUẢN TRỊ VIÊN", SwingConstants.CENTER);
        userInfoLabel = new JLabel("Chưa đăng nhập", SwingConstants.CENTER);
        statusLabel = new JLabel("Khởi tạo giao diện...", SwingConstants.CENTER);

        initLayout();
    }

    // Tạo khung giao diện dành riêng cho quản trị viên.
    private void initLayout() {
        Font titleFont = new Font("Segoe UI", Font.BOLD, 26);
        Font userFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font statusFont = new Font("Segoe UI", Font.ITALIC, 14);
        Font tabFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font contentFont = new Font("Segoe UI", Font.PLAIN, 20);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        mainPanel.setBackground(Color.WHITE);

        titleLabel.setFont(titleFont);
        userInfoLabel.setFont(userFont);
        statusLabel.setFont(statusFont);

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(userInfoLabel, BorderLayout.SOUTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(tabFont);
        tabbedPane.addTab("Quản lý tài khoản", createPlaceholderPanel("Màn hình quản lý tài khoản", contentFont));
        tabbedPane.addTab("Quản lý sách", createPlaceholderPanel("Màn hình quản lý sách", contentFont));
        tabbedPane.addTab("Quản lý sinh viên", createPlaceholderPanel("Màn hình quản lý sinh viên", contentFont));
        tabbedPane.addTab("Báo cáo", createPlaceholderPanel("Màn hình báo cáo thống kê", contentFont));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    // Tạo panel tạm để giữ chỗ cho chức năng sẽ làm sau.
    private JPanel createPlaceholderPanel(String text, Font contentFont) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(text);
        label.setFont(contentFont);
        panel.add(label);

        return panel;
    }

    // Cập nhật dòng trạng thái ở cuối màn hình.
    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    // Hiển thị thông tin tài khoản đang đăng nhập.
    public void setCurrentUserInfo(String username, Integer roleId, String roleName) {
        userInfoLabel.setText("Tài khoản: " + username + " | Role ID: " + roleId + " | Vai trò: " + roleName);
    }
}
