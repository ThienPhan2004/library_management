package com.laptrinhwindows.library_management.view;

import com.laptrinhwindows.library_management.view.manager.BookManagementPanel;
import com.laptrinhwindows.library_management.view.manager.BookTitleManagementPanel;
import com.laptrinhwindows.library_management.view.manager.ReportPanel;
import com.laptrinhwindows.library_management.view.manager.SupplierManagementPanel;
import com.laptrinhwindows.library_management.view.shared.StudentManagementPanel;
import com.laptrinhwindows.library_management.view.staff.BorrowOrderPanel;
import com.laptrinhwindows.library_management.view.staff.BorrowTrackingPanel;
import com.laptrinhwindows.library_management.view.staff.ReturnBookPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class StaffFrame extends JFrame implements LibraryWorkspaceFrame {
    private final JLabel titleLabel;
    private final JLabel userInfoLabel;
    private final JLabel statusLabel;
    private final JButton logoutButton;
    private final BorrowOrderPanel borrowOrderPanel;
    private final ReturnBookPanel returnBookPanel;
    private final StudentManagementPanel studentManagementPanel;
    private final BorrowTrackingPanel borrowTrackingPanel;

    public StaffFrame() {
        setTitle("Quản lý thư viện - Nhân viên");
        setSize(1120, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("GIAO DIỆN NHÂN VIÊN THƯ VIỆN", SwingConstants.CENTER);
        userInfoLabel = new JLabel("Chưa đăng nhập", SwingConstants.CENTER);
        statusLabel = new JLabel("Khởi tạo giao diện...", SwingConstants.CENTER);
        logoutButton = new JButton("Đăng xuất");
        borrowOrderPanel = new BorrowOrderPanel();
        returnBookPanel = new ReturnBookPanel();
        studentManagementPanel = new StudentManagementPanel();
        borrowTrackingPanel = new BorrowTrackingPanel();

        studentManagementPanel.setReadOnlyMode(true);
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
        tabbedPane.addTab("Mượn sách", borrowOrderPanel);
        tabbedPane.addTab("Trả sách", returnBookPanel);
        tabbedPane.addTab("Danh sách sinh viên", studentManagementPanel);
        tabbedPane.addTab("Lịch sử mượn trả", borrowTrackingPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    @Override
    public StudentManagementPanel getStudentManagementPanel() {
        return studentManagementPanel;
    }

    @Override
    public BookTitleManagementPanel getBookTitleManagementPanel() {
        return null;
    }

    @Override
    public BookManagementPanel getBookManagementPanel() {
        return null;
    }

    @Override
    public BorrowOrderPanel getBorrowOrderPanel() {
        return borrowOrderPanel;
    }

    @Override
    public ReturnBookPanel getReturnBookPanel() {
        return returnBookPanel;
    }

    @Override
    public BorrowTrackingPanel getBorrowTrackingPanel() {
        return borrowTrackingPanel;
    }

    @Override
    public SupplierManagementPanel getSupplierManagementPanel() {
        return null;
    }

    @Override
    public ReportPanel getReportPanel() {
        return null;
    }

    @Override
    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    @Override
    public void setCurrentUserInfo(String username, Integer roleId, String roleName) {
        userInfoLabel.setText("Tài khoản: " + username + " | Role ID: " + roleId + " | Vai trò: " + roleName);
    }

    public void addLogoutListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
}
