package com.laptrinhwindows.library_management.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    private final JLabel titleLabel;
    private final JLabel userInfoLabel;
    private final JLabel statusLabel;

    public MainFrame() {
        setTitle("Quản lý thư viện");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ THƯ VIỆN", SwingConstants.CENTER);
        userInfoLabel = new JLabel("Chưa đăng nhập", SwingConstants.CENTER);
        statusLabel = new JLabel("Khởi tạo giao diện...", SwingConstants.CENTER);

        initLayout();
    }

    // Tạo khung giao diện tổng quan cho hệ thống.
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
        titleLabel.setForeground(new Color(33, 37, 41));

        userInfoLabel.setFont(userFont);
        userInfoLabel.setForeground(new Color(80, 80, 80));

        statusLabel.setFont(statusFont);
        statusLabel.setForeground(new Color(0, 102, 204));

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(userInfoLabel, BorderLayout.SOUTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(tabFont);
        tabbedPane.addTab("Sách", createPlaceholderPanel("Màn hình quản lý sách", contentFont));
        tabbedPane.addTab("Sinh viên", createPlaceholderPanel("Màn hình quản lý sinh viên", contentFont));
        tabbedPane.addTab("Phiếu mượn", createPlaceholderPanel("Màn hình quản lý phiếu mượn", contentFont));

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
        label.setForeground(new Color(60, 60, 60));

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