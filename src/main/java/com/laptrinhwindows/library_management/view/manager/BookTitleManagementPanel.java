package com.laptrinhwindows.library_management.view.manager;

import com.laptrinhwindows.library_management.model.entity.BookTitle;
import com.laptrinhwindows.library_management.model.entity.Supplier;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class BookTitleManagementPanel extends JPanel {
    private final JTextField searchTitleField;
    private final JTextField searchAuthorField;
    private final JTextField searchCategoryField;
    private final JButton searchButton;
    private final JButton resetButton;

    private final JTable bookTitleTable;
    private final DefaultTableModel bookTitleTableModel;

    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField categoryField;
    private final JTextField publishYearField;
    private final JComboBox<SupplierItem> supplierComboBox;
    private final JComboBox<RecordStatus> statusComboBox;
    private final JButton addButton;
    private final JButton updateButton;
    private final JButton deleteButton;

    public BookTitleManagementPanel() {
        searchTitleField = new JTextField(14);
        searchAuthorField = new JTextField(14);
        searchCategoryField = new JTextField(12);
        searchButton = new JButton("Tìm kiếm");
        resetButton = new JButton("Làm mới");

        bookTitleTableModel = new DefaultTableModel(
                new Object[]{"ID", "Tên sách", "Tác giả", "Thể loại", "Năm XB", "Nhà cung cấp", "Trạng thái"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookTitleTable = new JTable(bookTitleTableModel);
        bookTitleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        titleField = new JTextField(20);
        authorField = new JTextField(20);
        categoryField = new JTextField(15);
        publishYearField = new JTextField(10);
        supplierComboBox = new JComboBox<>();
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
        searchPanel.add(new JLabel("Tên sách:"));
        searchPanel.add(searchTitleField);
        searchPanel.add(new JLabel("Tác giả:"));
        searchPanel.add(searchAuthorField);
        searchPanel.add(new JLabel("Thể loại:"));
        searchPanel.add(searchCategoryField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        JScrollPane tableScrollPane = new JScrollPane(bookTitleTable);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin đầu sách"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Tên sách:");
        JLabel authorLabel = new JLabel("Tác giả:");
        JLabel categoryLabel = new JLabel("Thể loại:");
        JLabel publishYearLabel = new JLabel("Năm xuất bản:");
        JLabel supplierLabel = new JLabel("Nhà cung cấp:");
        JLabel statusLabel = new JLabel("Trạng thái:");

        titleLabel.setFont(formFont);
        authorLabel.setFont(formFont);
        categoryLabel.setFont(formFont);
        publishYearLabel.setFont(formFont);
        supplierLabel.setFont(formFont);
        statusLabel.setFont(formFont);
        titleField.setFont(formFont);
        authorField.setFont(formFont);
        categoryField.setFont(formFont);
        publishYearField.setFont(formFont);
        supplierComboBox.setFont(formFont);
        statusComboBox.setFont(formFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(titleLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(authorLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(publishYearLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(publishYearField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(supplierLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(supplierComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(statusLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(statusComboBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    public void showBookTitles(List<BookTitle> bookTitles) {
        bookTitleTableModel.setRowCount(0);
        for (BookTitle bookTitle : bookTitles) {
            bookTitleTableModel.addRow(new Object[]{
                    bookTitle.getId(),
                    bookTitle.getTitle(),
                    bookTitle.getAuthor(),
                    bookTitle.getCategory(),
                    bookTitle.getPublishYear(),
                    bookTitle.getSupplier() == null ? "" : bookTitle.getSupplier().getName(),
                    bookTitle.getStatus()
            });
        }
    }

    public void setSupplierOptions(List<Supplier> suppliers) {
        DefaultComboBoxModel<SupplierItem> comboBoxModel = new DefaultComboBoxModel<>();
        for (Supplier supplier : suppliers) {
            comboBoxModel.addElement(new SupplierItem(supplier.getId(), supplier.getName()));
        }
        supplierComboBox.setModel(comboBoxModel);
        if (comboBoxModel.getSize() > 0) {
            supplierComboBox.setSelectedIndex(0);
        }
    }

    public void fillBookTitleForm(BookTitle bookTitle) {
        titleField.setText(bookTitle.getTitle());
        authorField.setText(bookTitle.getAuthor());
        categoryField.setText(bookTitle.getCategory());
        publishYearField.setText(bookTitle.getPublishYear() == null ? "" : String.valueOf(bookTitle.getPublishYear()));
        statusComboBox.setSelectedItem(bookTitle.getStatus() == null ? RecordStatus.ACTIVE : bookTitle.getStatus());

        Integer supplierId = bookTitle.getSupplier() == null ? null : bookTitle.getSupplier().getId();
        if (supplierId != null) {
            for (int i = 0; i < supplierComboBox.getItemCount(); i++) {
                SupplierItem item = supplierComboBox.getItemAt(i);
                if (supplierId.equals(item.getId())) {
                    supplierComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    public void clearBookTitleForm() {
        bookTitleTable.clearSelection();
        titleField.setText("");
        authorField.setText("");
        categoryField.setText("");
        publishYearField.setText("");
        statusComboBox.setSelectedItem(RecordStatus.ACTIVE);
        if (supplierComboBox.getItemCount() > 0) {
            supplierComboBox.setSelectedIndex(0);
        }
    }

    public void clearBookTitleSearchFields() {
        searchTitleField.setText("");
        searchAuthorField.setText("");
        searchCategoryField.setText("");
    }

    public Integer getSelectedBookTitleId() {
        int selectedRow = bookTitleTable.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        Object value = bookTitleTableModel.getValueAt(selectedRow, 0);
        return value instanceof Integer ? (Integer) value : null;
    }

    public String getSearchTitle() {
        return searchTitleField.getText().trim();
    }

    public String getSearchAuthor() {
        return searchAuthorField.getText().trim();
    }

    public String getSearchCategory() {
        return searchCategoryField.getText().trim();
    }

    public String getFormTitle() {
        return titleField.getText().trim();
    }

    public String getFormAuthor() {
        return authorField.getText().trim();
    }

    public String getFormCategory() {
        return categoryField.getText().trim();
    }

    public String getFormPublishYear() {
        return publishYearField.getText().trim();
    }

    public Integer getSelectedSupplierId() {
        SupplierItem item = (SupplierItem) supplierComboBox.getSelectedItem();
        return item == null ? null : item.getId();
    }

    public RecordStatus getFormStatus() {
        return (RecordStatus) statusComboBox.getSelectedItem();
    }

    public void addBookTitleSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addBookTitleResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    public void addBookTitleAddListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addBookTitleUpdateListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addBookTitleDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addBookTitleTableSelectionListener(ListSelectionListener listener) {
        bookTitleTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }

    public boolean confirmDeleteBookTitle() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Chuyển đầu sách này sang trạng thái INACTIVE?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );
        return choice == JOptionPane.YES_OPTION;
    }

    private static class SupplierItem {
        private final Integer id;
        private final String name;

        private SupplierItem(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
