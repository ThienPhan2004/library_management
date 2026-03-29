package com.laptrinhwindows.library_management.view.manager;

import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.BookTitle;
import com.laptrinhwindows.library_management.model.enumtype.BookStatus;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class BookManagementPanel extends JPanel {
    private final JTable bookTable;
    private final DefaultTableModel bookTableModel;
    private final JComboBox<BookTitleItem> bookTitleComboBox;
    private final JTextField locationField;
    private final JComboBox<BookStatus> statusComboBox;
    private final JButton addButton;
    private final JButton updateButton;

    public BookManagementPanel() {
        bookTableModel = new DefaultTableModel(
                new Object[]{"ID", "Đầu sách", "Tác giả", "Vị trí", "Trạng thái"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookTable = new JTable(bookTableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        bookTitleComboBox = new JComboBox<>();
        locationField = new JTextField(15);
        statusComboBox = new JComboBox<>(BookStatus.values());
        addButton = new JButton("Nhập cuốn");
        updateButton = new JButton("Cập nhật");

        initLayout();
    }

    // Tạo giao diện riêng cho chức năng quản lý cuốn sách.
    private void initLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        Font formFont = new Font("Segoe UI", Font.PLAIN, 14);

        JScrollPane tableScrollPane = new JScrollPane(bookTable);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin cuốn sách"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel bookTitleLabel = new JLabel("Đầu sách:");
        JLabel locationLabel = new JLabel("Vị trí:");
        JLabel statusLabel = new JLabel("Trạng thái:");

        bookTitleLabel.setFont(formFont);
        locationLabel.setFont(formFont);
        statusLabel.setFont(formFont);
        bookTitleComboBox.setFont(formFont);
        locationField.setFont(formFont);
        statusComboBox.setFont(formFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(bookTitleLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(bookTitleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(locationLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(locationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(statusLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(statusComboBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    // Hiển thị danh sách cuốn sách lên bảng.
    public void showBooks(List<Book> books) {
        bookTableModel.setRowCount(0);
        for (Book book : books) {
            String title = book.getBookTitle() != null ? book.getBookTitle().getTitle() : "";
            String author = book.getBookTitle() != null ? book.getBookTitle().getAuthor() : "";
            bookTableModel.addRow(new Object[]{
                    book.getId(),
                    title,
                    author,
                    book.getLocation(),
                    book.getStatus()
            });
        }
    }

    // Nạp danh sách đầu sách vào combobox để người dùng chọn khi nhập cuốn mới.
    public void setBookTitleOptions(List<BookTitle> bookTitles) {
        DefaultComboBoxModel<BookTitleItem> comboBoxModel = new DefaultComboBoxModel<>();
        for (BookTitle bookTitle : bookTitles) {
            comboBoxModel.addElement(new BookTitleItem(bookTitle.getId(), bookTitle.getTitle(), bookTitle.getAuthor()));
        }
        bookTitleComboBox.setModel(comboBoxModel);
        if (comboBoxModel.getSize() > 0) {
            bookTitleComboBox.setSelectedIndex(0);
        }
    }

    // Đổ dữ liệu cuốn sách được chọn lên form.
    public void fillBookForm(Book book) {
        locationField.setText(book.getLocation() == null ? "" : book.getLocation());
        statusComboBox.setSelectedItem(book.getStatus());

        if (book.getBookTitle() != null) {
            for (int i = 0; i < bookTitleComboBox.getItemCount(); i++) {
                BookTitleItem item = bookTitleComboBox.getItemAt(i);
                if (item.getId().equals(book.getBookTitle().getId())) {
                    bookTitleComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    // Xóa trắng form nhập cuốn sách.
    public void clearBookForm() {
        bookTable.clearSelection();
        locationField.setText("");
        if (statusComboBox.getItemCount() > 0) {
            statusComboBox.setSelectedItem(BookStatus.AVAILABLE);
        }
    }

    // Lấy mã định danh của cuốn sách đang được chọn trên bảng.
    public Integer getSelectedBookId() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        Object value = bookTableModel.getValueAt(selectedRow, 0);
        return value instanceof Integer ? (Integer) value : null;
    }

    public Integer getSelectedBookTitleId() {
        BookTitleItem item = (BookTitleItem) bookTitleComboBox.getSelectedItem();
        return item == null ? null : item.getId();
    }

    public String getFormLocation() {
        return locationField.getText().trim();
    }

    public BookStatus getFormStatus() {
        return (BookStatus) statusComboBox.getSelectedItem();
    }

    // Gắn sự kiện thêm cuốn sách.
    public void addBookAddListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    // Gắn sự kiện cập nhật cuốn sách.
    public void addBookUpdateListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    // Gắn sự kiện chọn dòng trên bảng cuốn sách.
    public void addBookTableSelectionListener(ListSelectionListener listener) {
        bookTable.getSelectionModel().addListSelectionListener(listener);
    }

    // Hiển thị hộp thoại báo lỗi cho người dùng.
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }

    private static class BookTitleItem {
        private final Integer id;
        private final String displayText;

        private BookTitleItem(Integer id, String title, String author) {
            this.id = id;
            this.displayText = title + " - " + author;
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
