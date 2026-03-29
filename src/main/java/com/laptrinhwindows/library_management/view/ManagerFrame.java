package com.laptrinhwindows.library_management.view;

import com.laptrinhwindows.library_management.view.manager.BookManagementPanel;
import com.laptrinhwindows.library_management.view.manager.BookTitleManagementPanel;
import com.laptrinhwindows.library_management.view.manager.BorrowOrderPanel;
import com.laptrinhwindows.library_management.view.manager.BorrowTrackingPanel;
import com.laptrinhwindows.library_management.view.manager.ReportPanel;
import com.laptrinhwindows.library_management.view.manager.ReturnBookPanel;
import com.laptrinhwindows.library_management.view.manager.StudentManagementPanel;
import com.laptrinhwindows.library_management.view.manager.SupplierManagementPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagerFrame extends JFrame {
    private final JLabel titleLabel;
    private final JLabel userInfoLabel;
    private final JLabel statusLabel;
    private final StudentManagementPanel studentManagementPanel;
    private final BookTitleManagementPanel bookTitleManagementPanel;
    private final BookManagementPanel bookManagementPanel;
    private final BorrowOrderPanel borrowOrderPanel;
    private final ReturnBookPanel returnBookPanel;
    private final BorrowTrackingPanel borrowTrackingPanel;
    private final SupplierManagementPanel supplierManagementPanel;
    private final ReportPanel reportPanel;

    public ManagerFrame() {
        setTitle("Quản lý thư viện - Quản lý");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("GIAO DIỆN QUẢN LÝ THƯ VIỆN", SwingConstants.CENTER);
        userInfoLabel = new JLabel("Chưa đăng nhập", SwingConstants.CENTER);
        statusLabel = new JLabel("Khởi tạo giao diện...", SwingConstants.CENTER);
        studentManagementPanel = new StudentManagementPanel();
        bookTitleManagementPanel = new BookTitleManagementPanel();
        bookManagementPanel = new BookManagementPanel();
        borrowOrderPanel = new BorrowOrderPanel();
        returnBookPanel = new ReturnBookPanel();
        borrowTrackingPanel = new BorrowTrackingPanel();
        supplierManagementPanel = new SupplierManagementPanel();
        reportPanel = new ReportPanel();

        initLayout();
    }

    // Tạo khung giao diện chính dành cho quản lý thư viện.
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

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(userInfoLabel, BorderLayout.SOUTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(tabFont);
        tabbedPane.addTab("Mượn sách", borrowOrderPanel);
        tabbedPane.addTab("Trả sách", returnBookPanel);
        tabbedPane.addTab("Quản lý học sinh", studentManagementPanel);
        tabbedPane.addTab("Quản lý đầu sách", bookTitleManagementPanel);
        tabbedPane.addTab("Quản lý cuốn sách", bookManagementPanel);
        tabbedPane.addTab("Theo dõi mượn", borrowTrackingPanel);
        tabbedPane.addTab("Nhà cung cấp", supplierManagementPanel);
        tabbedPane.addTab("Thống kê", reportPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public StudentManagementPanel getStudentManagementPanel() {
        return studentManagementPanel;
    }

    public BookTitleManagementPanel getBookTitleManagementPanel() {
        return bookTitleManagementPanel;
    }

    public BookManagementPanel getBookManagementPanel() {
        return bookManagementPanel;
    }

    public BorrowOrderPanel getBorrowOrderPanel() {
        return borrowOrderPanel;
    }

    public ReturnBookPanel getReturnBookPanel() {
        return returnBookPanel;
    }

    public BorrowTrackingPanel getBorrowTrackingPanel() {
        return borrowTrackingPanel;
    }

    public SupplierManagementPanel getSupplierManagementPanel() {
        return supplierManagementPanel;
    }

    public ReportPanel getReportPanel() {
        return reportPanel;
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
