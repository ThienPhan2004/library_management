package com.laptrinhwindows.library_management.view.admin;

import com.laptrinhwindows.library_management.model.entity.Role;
import com.laptrinhwindows.library_management.model.entity.User;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class AccountManagementPanel extends JPanel {
    private final JTextField searchUsernameField;
    private final JComboBox<RoleItem> searchRoleComboBox;
    private final JButton searchButton;
    private final JButton resetButton;

    private final JTable userTable;
    private final DefaultTableModel userTableModel;

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JComboBox<RoleItem> roleComboBox;
    private final JButton addButton;
    private final JButton updateButton;
    private final JButton deleteButton;

    public AccountManagementPanel() {
        searchUsernameField = new JTextField(14);
        searchRoleComboBox = new JComboBox<>();
        searchButton = new JButton("Tìm kiếm");
        resetButton = new JButton("Làm mới");

        userTableModel = new DefaultTableModel(
                new Object[]{"ID", "Tên đăng nhập", "Vai trò"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(userTableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        usernameField = new JTextField(18);
        passwordField = new JPasswordField(18);
        roleComboBox = new JComboBox<>();
        addButton = new JButton("Thêm");
        updateButton = new JButton("Cập nhật");
        deleteButton = new JButton("Xóa");

        initLayout();
    }

    private void initLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        Font formFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Tên đăng nhập:"));
        searchPanel.add(searchUsernameField);
        searchPanel.add(new JLabel("Vai trò:"));
        searchPanel.add(searchRoleComboBox);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        JScrollPane tableScrollPane = new JScrollPane(userTable);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        JLabel roleLabel = new JLabel("Vai trò:");
        JLabel noteLabel = new JLabel("Để trống mật khẩu khi cập nhật nếu muốn giữ nguyên.");

        usernameLabel.setFont(formFont);
        passwordLabel.setFont(formFont);
        roleLabel.setFont(formFont);
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        usernameField.setFont(formFont);
        passwordField.setFont(formFont);
        roleComboBox.setFont(formFont);
        searchUsernameField.setFont(formFont);
        searchRoleComboBox.setFont(formFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(roleLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(noteLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridy = 4;
        formPanel.add(buttonPanel, gbc);

        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    public void setRoleOptions(List<Role> roles) {
        DefaultComboBoxModel<RoleItem> formModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<RoleItem> searchModel = new DefaultComboBoxModel<>();

        searchModel.addElement(new RoleItem(null, "Tất cả vai trò"));
        for (Role role : roles) {
            RoleItem item = new RoleItem(role.getId(), resolveRoleDisplayName(role));
            formModel.addElement(item);
            searchModel.addElement(item);
        }

        roleComboBox.setModel(formModel);
        searchRoleComboBox.setModel(searchModel);

        if (formModel.getSize() > 0) {
            roleComboBox.setSelectedIndex(0);
        }
        searchRoleComboBox.setSelectedIndex(0);
    }

    public void showUsers(List<User> users) {
        userTableModel.setRowCount(0);
        for (User user : users) {
            userTableModel.addRow(new Object[]{
                    user.getId(),
                    user.getUsername(),
                    user.getRole() == null ? "" : resolveRoleDisplayName(user.getRole())
            });
        }
    }

    public void fillUserForm(User user) {
        usernameField.setText(user.getUsername() == null ? "" : user.getUsername());
        passwordField.setText("");

        Integer roleId = user.getRole() == null ? null : user.getRole().getId();
        for (int i = 0; i < roleComboBox.getItemCount(); i++) {
            RoleItem item = roleComboBox.getItemAt(i);
            if (item.getId() != null && item.getId().equals(roleId)) {
                roleComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    public void clearForm() {
        userTable.clearSelection();
        usernameField.setText("");
        passwordField.setText("");
        if (roleComboBox.getItemCount() > 0) {
            roleComboBox.setSelectedIndex(0);
        }
    }

    public void clearSearchFields() {
        searchUsernameField.setText("");
        if (searchRoleComboBox.getItemCount() > 0) {
            searchRoleComboBox.setSelectedIndex(0);
        }
    }

    public Integer getSelectedUserId() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        Object value = userTableModel.getValueAt(selectedRow, 0);
        return value instanceof Integer ? (Integer) value : null;
    }

    public String getSearchUsername() {
        return searchUsernameField.getText().trim();
    }

    public Integer getSearchRoleId() {
        RoleItem item = (RoleItem) searchRoleComboBox.getSelectedItem();
        return item == null ? null : item.getId();
    }

    public String getFormUsername() {
        return usernameField.getText().trim();
    }

    public String getFormPassword() {
        return new String(passwordField.getPassword()).trim();
    }

    public Integer getFormRoleId() {
        RoleItem item = (RoleItem) roleComboBox.getSelectedItem();
        return item == null ? null : item.getId();
    }

    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    public void addAddListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addTableSelectionListener(ListSelectionListener listener) {
        userTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }

    public boolean confirmDeleteUser(String username) {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa tài khoản \"" + username + "\" không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );
        return choice == JOptionPane.YES_OPTION;
    }

    private String resolveRoleDisplayName(Role role) {
        if (role == null || role.getId() == null) {
            return "";
        }
        if (role.getId() == 1) {
            return "Quản trị viên";
        }
        if (role.getId() == 2) {
            return "Quản lý thư viện";
        }
        if (role.getId() == 3) {
            return "Nhân viên";
        }
        
        return role.getName();
    }

    private static class RoleItem {
        private final Integer id;
        private final String displayText;

        private RoleItem(Integer id, String displayText) {
            this.id = id;
            this.displayText = displayText;
        }

        public Integer getId() {
            return id;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }
}
