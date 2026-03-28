package com.laptrinhwindows.library_management.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel messageLabel;
    private final JButton loginButton;

    public LoginFrame() {
        setTitle("Đăng nhập");
        setSize(480, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        messageLabel = new JLabel("Nhập tài khoản để truy cập hệ thống.", SwingConstants.CENTER);
        loginButton = new JButton("Đăng nhập");

        initLayout();
    }

    // Khởi tạo giao diện đăng nhập đẹp hơn và dễ nhìn hơn.
    private void initLayout() {
        Font titleFont = new Font("Segoe UI", Font.BOLD, 22);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15);
        Font messageFont = new Font("Segoe UI", Font.ITALIC, 13);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ THƯ VIỆN", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(33, 37, 41));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameLabel.setFont(labelFont);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setFont(labelFont);

        usernameField.setFont(inputFont);
        passwordField.setFont(inputFont);

        loginButton.setFont(buttonFont);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(140, 38));

        messageLabel.setFont(messageFont);
        messageLabel.setForeground(new Color(220, 53, 69));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginButton, gbc);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(messageLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    // Lấy tên đăng nhập người dùng đã nhập.
    public String getUsername() {
        return usernameField.getText().trim();
    }

    // Lấy mật khẩu người dùng đã nhập.
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // Xóa trắng ô mật khẩu sau khi đăng nhập thất bại hoặc cần nhập lại.
    public void clearPassword() {
        passwordField.setText("");
    }

    // Hiển thị thông báo cho người dùng ở cuối form.
    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    // Gắn sự kiện đăng nhập cho nút bấm và cả phím Enter ở ô mật khẩu.
    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
        passwordField.addActionListener(listener);
    }
}