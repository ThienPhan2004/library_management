package com.laptrinhwindows.library_management.view.manager;

import com.laptrinhwindows.library_management.model.entity.Supplier;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SupplierManagementPanel extends JPanel {
    private final JTextField searchNameField;
    private final JTextField searchPhoneField;
    private final JTextField searchEmailField;
    private final JButton searchButton;
    private final JButton resetButton;

    private final JTable supplierTable;
    private final DefaultTableModel supplierTableModel;

    private final JTextField nameField;
    private final JTextField phoneField;
    private final JTextField emailField;
    private final JTextField addressField;
    private final JComboBox<RecordStatus> statusComboBox;
    private final JButton addButton;
    private final JButton updateButton;
    private final JButton deleteButton;

    public SupplierManagementPanel() {
        searchNameField = new JTextField(14);
        searchPhoneField = new JTextField(12);
        searchEmailField = new JTextField(16);
        searchButton = new JButton("Tìm kiếm");
        resetButton = new JButton("Làm mới");

        supplierTableModel = new DefaultTableModel(
                new Object[]{"ID", "Tên nhà cung cấp", "Số điện thoại", "Email", "Địa chỉ", "Trạng thái"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        supplierTable = new JTable(supplierTableModel);
        supplierTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierTable.setRowHeight(24);

        nameField = new JTextField(20);
        phoneField = new JTextField(15);
        emailField = new JTextField(20);
        addressField = new JTextField(25);
        statusComboBox = new JComboBox<>(RecordStatus.values());
        addButton = new JButton("Thêm");
        updateButton = new JButton("Sửa");
        deleteButton = new JButton("Ngừng sử dụng");

        initLayout();
    }

    private void initLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        Font formFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Tên NCC:"));
        searchPanel.add(searchNameField);
        searchPanel.add(new JLabel("Số điện thoại:"));
        searchPanel.add(searchPhoneField);
        searchPanel.add(new JLabel("Email:"));
        searchPanel.add(searchEmailField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        JScrollPane tableScrollPane = new JScrollPane(supplierTable);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhà cung cấp"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Tên nhà cung cấp:");
        JLabel phoneLabel = new JLabel("Số điện thoại:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel addressLabel = new JLabel("Địa chỉ:");
        JLabel statusLabel = new JLabel("Trạng thái:");

        nameLabel.setFont(formFont);
        phoneLabel.setFont(formFont);
        emailLabel.setFont(formFont);
        addressLabel.setFont(formFont);
        statusLabel.setFont(formFont);
        nameField.setFont(formFont);
        phoneField.setFont(formFont);
        emailField.setFont(formFont);
        addressField.setFont(formFont);
        statusComboBox.setFont(formFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(statusLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(statusComboBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    public void showSuppliers(List<Supplier> suppliers) {
        supplierTableModel.setRowCount(0);
        for (Supplier supplier : suppliers) {
            supplierTableModel.addRow(new Object[]{
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getPhone(),
                    supplier.getEmail(),
                    supplier.getAddress(),
                    supplier.getStatus()
            });
        }
    }

    public void fillSupplierForm(Supplier supplier) {
        nameField.setText(supplier.getName() == null ? "" : supplier.getName());
        phoneField.setText(supplier.getPhone() == null ? "" : supplier.getPhone());
        emailField.setText(supplier.getEmail() == null ? "" : supplier.getEmail());
        addressField.setText(supplier.getAddress() == null ? "" : supplier.getAddress());
        statusComboBox.setSelectedItem(supplier.getStatus() == null ? RecordStatus.ACTIVE : supplier.getStatus());
    }

    public void clearSupplierForm() {
        supplierTable.clearSelection();
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        statusComboBox.setSelectedItem(RecordStatus.ACTIVE);
    }

    public void clearSupplierSearchFields() {
        searchNameField.setText("");
        searchPhoneField.setText("");
        searchEmailField.setText("");
    }

    public Integer getSelectedSupplierId() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        Object value = supplierTableModel.getValueAt(selectedRow, 0);
        return value instanceof Integer ? (Integer) value : null;
    }

    public String getSearchName() {
        return searchNameField.getText().trim();
    }

    public String getSearchPhone() {
        return searchPhoneField.getText().trim();
    }

    public String getSearchEmail() {
        return searchEmailField.getText().trim();
    }

    public String getFormName() {
        return nameField.getText().trim();
    }

    public String getFormPhone() {
        return phoneField.getText().trim();
    }

    public String getFormEmail() {
        return emailField.getText().trim();
    }

    public String getFormAddress() {
        return addressField.getText().trim();
    }

    public RecordStatus getFormStatus() {
        return (RecordStatus) statusComboBox.getSelectedItem();
    }

    public void addSupplierSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addSupplierResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    public void addSupplierAddListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addSupplierUpdateListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addSupplierDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addSupplierTableSelectionListener(ListSelectionListener listener) {
        supplierTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }

    public boolean confirmDeleteSupplier() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Chuyển nhà cung cấp này sang trạng thái INACTIVE?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );
        return choice == JOptionPane.YES_OPTION;
    }
}
