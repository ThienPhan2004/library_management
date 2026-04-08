package com.laptrinhwindows.library_management.view;

import com.laptrinhwindows.library_management.view.admin.AccountManagementPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame {
    private final JLabel titleLabel;
    private final JLabel userInfoLabel;
    private final JLabel statusLabel;
    private final JButton logoutButton;
    private final AccountManagementPanel accountManagementPanel;

    public AdminFrame() {
        setTitle("Quản lý thư viện - Admin");
        setSize(980, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("GIAO DIỆN QUẢN TRỊ TÀI KHOẢN", SwingConstants.CENTER);
        userInfoLabel = new JLabel("Chưa đăng nhập", SwingConstants.CENTER);
        statusLabel = new JLabel("Khởi tạo giao diện...", SwingConstants.CENTER);
        logoutButton = new JButton("Đăng xuất");
        accountManagementPanel = new AccountManagementPanel();

        initLayout();
    }

    private void initLayout() {
        Font titleFont = new Font("Segoe UI", Font.BOLD, 26);
        Font userFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font statusFont = new Font("Segoe UI", Font.ITALIC, 14);
        Font tabFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        mainPanel.setBackground(Color.WHITE);

        titleLabel.setFont(titleFont);
        userInfoLabel.setFont(userFont);
        statusLabel.setFont(statusFont);

        JPanel topPanel = new JPanel(new BorderLayout(10, 5));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(userInfoLabel, BorderLayout.CENTER);
        topPanel.add(logoutButton, BorderLayout.EAST);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(tabFont);
        tabbedPane.addTab("Quản lý tài khoản", accountManagementPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public AccountManagementPanel getAccountManagementPanel() {
        return accountManagementPanel;
    }

    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    public void setCurrentUserInfo(String username, Integer roleId, String roleName) {
        userInfoLabel.setText("Tài khoản: " + username + " | Role ID: " + roleId + " | Vai trò: " + roleName);
    }

    public void addLogoutListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
}
